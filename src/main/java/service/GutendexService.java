package com.riane.catalogo.service;

import com.riane.catalogo.entity.Livro;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Map;

@Service
public class GutendexService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "https://gutendex.com/books";

    public Livro buscarPorTitulo(String titulo) {
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("search", titulo)
                .toUriString();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("results")) {
                var results = (java.util.List<Map<String, Object>>) response.get("results");
                if (!results.isEmpty()) {
                    Map<String, Object> livroMap = results.get(0);
                    String bookTitle = (String) livroMap.get("title");
                    String autor = ((java.util.List<Map<String, Object>>) livroMap.get("authors"))
                            .stream()
                            .map(a -> (String) a.get("name"))
                            .findFirst().orElse("Autor desconhecido");
                    int ano = 0;
                    String idioma = ((java.util.List<String>) livroMap.get("languages"))
                            .stream()
                            .findFirst().orElse("desconhecido");
                    return new Livro(bookTitle, autor, ano, idioma);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar API Gutendex: " + e.getMessage());
        }
        return null;
    }
}
