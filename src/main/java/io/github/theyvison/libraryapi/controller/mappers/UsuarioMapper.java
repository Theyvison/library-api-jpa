package io.github.theyvison.libraryapi.controller.mappers;

import io.github.theyvison.libraryapi.controller.dto.UsuarioDTO;
import io.github.theyvison.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
