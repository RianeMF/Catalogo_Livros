package com.riane.catalogo.repository;

import com.riane.catalogo.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByAno(int ano);
    List<Livro> findByIdioma(String idioma);
}
