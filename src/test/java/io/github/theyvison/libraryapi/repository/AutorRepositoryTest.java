package io.github.theyvison.libraryapi.repository;

import io.github.theyvison.libraryapi.model.Autor;
import io.github.theyvison.libraryapi.model.GeneroLivro;
import io.github.theyvison.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1955, 3, 14));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void AtualizarTest() {
        var id = UUID.fromString("25335a11-c2a6-467d-9a23-6dfc9fab6cda");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 31));

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = autorRepository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void contarTest() {
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deletarPorIdTest() {
        var id = UUID.fromString("efc066e9-97c2-48f9-a65e-36e684a5b784");
        autorRepository.deleteById(id);
    }

    @Test
    public void deletarTest() {
        var id = UUID.fromString("317c701d-178b-46d6-8dbf-3875392be7c9");
        var maria = autorRepository.findById(id).get();
        autorRepository.delete(maria);
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Antony");
        autor.setNacionalidade("Americana");
        autor.setDataNascimento(LocalDate.of(1980, 7, 10));

        Livro livro = new Livro();
        livro.setIsbn("14856-19632");
        livro.setPreco(BigDecimal.valueOf(80));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("O roubo da casa assombrada");
        livro.setDataPublicacao(LocalDate.of(2005, 4, 1));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("14856-19632");
        livro2.setPreco(BigDecimal.valueOf(100));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("P.E.M.A.N");
        livro2.setDataPublicacao(LocalDate.of(2008, 10, 15));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }
}
