package io.github.theyvison.libraryapi.repository;

import io.github.theyvison.libraryapi.model.Autor;
import io.github.theyvison.libraryapi.model.GeneroLivro;
import io.github.theyvison.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID idLivro = UUID.fromString("cbc7e629-5bda-4359-9bce-863143c78c56");
        Livro livro = livroRepository.findById(idLivro).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor(a):");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> lista = livroRepository.findByTitulo("O roubo da casa assombrada");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest() {
        List<Livro> lista = livroRepository.findByIsbn("84562-21428");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPreco() {
        List<Livro> lista = livroRepository.findByTituloAndPreco("P.E.M.A.N", BigDecimal.valueOf(100.00));
    }
}