package io.github.theyvison.libraryapi.repository;

import io.github.theyvison.libraryapi.model.Autor;
import io.github.theyvison.libraryapi.model.GeneroLivro;
import io.github.theyvison.libraryapi.model.Livro;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("84562-21428");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository
                .findById(UUID
                .fromString("6bd2707e-af0d-49e3-9569-c247f22ceeb8"))
                .orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("58413-99856");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro livro");
        livro.setDataPublicacao(LocalDate.of(1999, 4, 16));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1960, 8, 11));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        var idLivro = UUID.fromString("952e56f4-db95-45f2-95bc-9aab3b9d2048");
        var livroParaAtualizar = livroRepository.findById(idLivro).orElse(null);

        var idAutor = UUID.fromString("25335a11-c2a6-467d-9a23-6dfc9fab6cda");
        Autor novoAutor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(novoAutor);
        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        var idLivro = UUID.fromString("952e56f4-db95-45f2-95bc-9aab3b9d2048");
        livroRepository.deleteById(idLivro);
    }
}