package com.riane.catalogo;

import com.riane.catalogo.entity.Livro;
import com.riane.catalogo.service.CatalogoService;
import com.riane.catalogo.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

    private final CatalogoService catalogoService;
    private final GutendexService gutendexService;

    // Scanner compartilhado
    private final Scanner scanner = new Scanner(System.in);

    public CatalogoApplication(CatalogoService catalogoService, GutendexService gutendexService) {
        this.catalogoService = catalogoService;
        this.gutendexService = gutendexService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CatalogoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        boolean running = true;

        while (running) {
            System.out.println("\n===== MENU CATÁLOGO DE LIVROS =====");
            System.out.println("1 - Buscar livro pelo título (API)");
            System.out.println("2 - Listar livros cadastrados");
            System.out.println("3 - Listar autores");
            System.out.println("4 - Listar livros por ano");
            System.out.println("5 - Listar livros por idioma");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");

            String input = scanner.nextLine();

            try {
                int opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1 -> buscarLivroAPI();
                    case 2 -> listarLivros();
                    case 3 -> listarAutores();
                    case 4 -> listarPorAno();
                    case 5 -> listarPorIdioma();
                    case 6 -> {
                        System.out.println("Saindo...");
                        running = false;
                    }
                    default -> System.out.println("Opção inválida!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números válidos!");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
    }

    private void buscarLivroAPI() {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        if (!titulo.trim().isEmpty()) {
            Livro livro = gutendexService.buscarPorTitulo(titulo);
            if (livro != null) {
                System.out.println("Livro inserido: " + livro.getTitulo() + " | Autor: " + livro.getAutor());
            } else {
                System.out.println("Livro não encontrado na API.");
            }
        }
    }

    private void listarLivros() {
        List<Livro> lista = catalogoService.listarLivros();
        if (lista.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            lista.forEach(l -> System.out.println("- " + l.getTitulo()));
        }
    }

    private void listarAutores() {
        List<String> autores = catalogoService.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
        } else {
            autores.forEach(a -> System.out.println("- " + a));
        }
    }

    private void listarPorAno() {
        System.out.print("Digite o ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        List<Livro> livros = catalogoService.listarAutoresPorAno(ano);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no ano " + ano);
        } else {
            livros.forEach(l -> System.out.println("- " + l.getTitulo()));
        }
    }

    private void listarPorIdioma() {
        System.out.print("Digite o idioma (ex: en, pt, es, fr): ");
        String idioma = scanner.nextLine();
        List<Livro> livros = catalogoService.listarLivrosPorIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no idioma " + idioma);
        } else {
            livros.forEach(l -> System.out.println("- " + l.getTitulo()));
        }
    }
}
