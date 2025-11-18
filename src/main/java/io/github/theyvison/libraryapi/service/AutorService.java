package io.github.theyvison.libraryapi.service;

import io.github.theyvison.libraryapi.model.Autor;
import io.github.theyvison.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor) {
        return autorRepository.save(autor);
    }
}