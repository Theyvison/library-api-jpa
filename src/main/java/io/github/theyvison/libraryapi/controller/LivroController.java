package io.github.theyvison.libraryapi.controller;

import io.github.theyvison.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.theyvison.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.theyvison.libraryapi.controller.mappers.LivroMapper;
import io.github.theyvison.libraryapi.model.Livro;
import io.github.theyvison.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") String id) {
        return livroService.obterPorId(UUID.fromString(id)).map(livro -> {
            var dto = livroMapper.toDTO(livro);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
