package io.github.theyvison.libraryapi.controller;

import io.github.theyvison.libraryapi.controller.dto.AutorDTO;
import io.github.theyvison.libraryapi.controller.mappers.AutorMapper;
import io.github.theyvison.libraryapi.model.Autor;
import io.github.theyvison.libraryapi.service.AutorService;
import jakarta.servlet.ServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorMapper autorMapper;
    private final AutorService autorService;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = autorMapper.toEntity(dto);
        autorService.salvar(autor);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'OPERADOR')")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return autorService
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autorOptional.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'OPERADOR')")
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade, ServletRequest servletRequest) {
        List<Autor> resultado = autorService.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> autores = resultado
                .stream()
                .map(autorMapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(autores);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}