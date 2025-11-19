package io.github.theyvison.libraryapi.controller;

import io.github.theyvison.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.theyvison.libraryapi.controller.dto.ErroResposta;
import io.github.theyvison.libraryapi.controller.mappers.LivroMapper;
import io.github.theyvison.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.theyvison.libraryapi.model.Livro;
import io.github.theyvison.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = livroMapper.toEntity(dto);
            livroService.salvar(livro);
            var url = gerarHeaderLocation(livro.getId());
            return ResponseEntity.created(url).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
