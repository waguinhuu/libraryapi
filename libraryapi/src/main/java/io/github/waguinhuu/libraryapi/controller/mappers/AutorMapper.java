package io.github.waguinhuu.libraryapi.controller.mappers;

import io.github.waguinhuu.libraryapi.controller.dto.AutorDTO;
import io.github.waguinhuu.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
