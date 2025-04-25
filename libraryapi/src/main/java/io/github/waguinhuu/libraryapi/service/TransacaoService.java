package io.github.waguinhuu.libraryapi.service;

import io.github.waguinhuu.libraryapi.model.Autor;
import io.github.waguinhuu.libraryapi.model.GeneroLivro;
import io.github.waguinhuu.libraryapi.model.Livro;
import io.github.waguinhuu.libraryapi.repository.AutorRepository;
import io.github.waguinhuu.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;
    

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("ea4a953b-3487-4a8f-a89c-ac16dab1e14e"))
                .orElse(null);
        livro.setDataPublicacao(LocalDate.of(2024,6,1));


    }

    @Transactional
    public void executar(){

        // Salva o autor
        Autor autor = new Autor();
        autor.setNome("Teste Francisco");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1931,2,10));

        autorRepository.save(autor);

        // Salva o livro
        Livro livro = new Livro();
        livro.setIsbn("1234567890");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Teste Livro do Francisco");
        livro.setDataPublicacao(LocalDate.of(2001,1,1));

        livro.setAutor(autor);
        livroRepository.save(livro);

        if(autor.getNome().equals(("Teste Francisco"))){
            throw new RuntimeException("Rollback");
        }
    }
}
