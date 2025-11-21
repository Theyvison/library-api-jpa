package io.github.theyvison.libraryapi.service;

import io.github.theyvison.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.theyvison.libraryapi.model.Autor;
import io.github.theyvison.libraryapi.model.Usuario;
import io.github.theyvison.libraryapi.repository.AutorRepository;
import io.github.theyvison.libraryapi.repository.LivroRepository;
import io.github.theyvison.libraryapi.security.SecurityService;
import io.github.theyvison.libraryapi.validator.AutorValidador;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;
    private final AutorValidador autorValidador;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public Autor salvar(Autor autor) {
        autorValidador.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
        }
        autorValidador.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor que possui livros cadastrados!");
        }
        autorRepository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null) {
            return autorRepository.findByNome(nome);
        }

        if (nacionalidade != null) {
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}