package io.github.waguinhuu.libraryapi.security;

import io.github.waguinhuu.libraryapi.model.Usuario;
import io.github.waguinhuu.libraryapi.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

    private final UsuarioService usuarioService;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication authenticaton = SecurityContextHolder.getContext().getAuthentication();

        if(deveConverter(authenticaton)){
            String login = authenticaton.getName();
            Usuario usuario = usuarioService.obterPorLogin(login);
            if(usuario!= null){
                authenticaton = new CustomAuthentication(usuario);
                SecurityContextHolder.getContext().setAuthentication(authenticaton);            }
        }

        filterChain.doFilter(request, response);

    }

    private boolean deveConverter(Authentication authentication){
        return authentication != null && authentication instanceof JwtAuthenticationToken;
    }
}
