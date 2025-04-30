package io.github.waguinhuu.libraryapi.controller.mappers;

import io.github.waguinhuu.libraryapi.controller.dto.UsuarioDTO;
import io.github.waguinhuu.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
