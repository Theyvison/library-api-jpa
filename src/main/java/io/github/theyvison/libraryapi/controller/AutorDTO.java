package io.github.theyvison.libraryapi.controller;

import java.time.LocalDate;

public record AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade) {
}
