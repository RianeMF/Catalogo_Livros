package com.riane.catalogo.service;

import com.riane.catalogo.entity.Livro;
import com.riane.catalogo.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogoService {

    private final LivroRepository livroRepository;

    public CatalogoService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void salvarLivro(Livro livro) {
        livroRepository.save(livro);
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public List<String> listarAutores() {
        return livroRepository.findAll()
                .stream()
                .map(Livro::getAutor)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Livro> listarAutoresPorAno(int ano) {
        return livroRepository.findByAno(ano);
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }
}
