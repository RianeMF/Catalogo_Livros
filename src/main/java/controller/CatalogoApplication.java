package com.riane.catalogo;

import com.riane.catalogo.entity.Livro;
import com.riane.catalogo.service.CatalogoService;
import com.riane.catalogo.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.JOptionPane;
import java.util.List;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

    private final CatalogoService catalogoService;
    private final GutendexService gutendexService;

    // Construtor
    public CatalogoApplication(CatalogoService catalogoService, GutendexService gutendexService) {
        this.catalogoService = catalogoService;
        this.gutendexService = gutendexService;
    }

    // Ponto de entrada do Spring Boot
    public static void main(String[] args) {
        SpringApplication.run(CatalogoApplication.class, args);
    }

    // Menu interativo
    @Override
    public void run(String... args) {
        int opcao = 0;

        while (opcao != 6) {
            try {
                String input = JOptionPane.showInputDialog(
                        "===== MENU CATÁLOGO DE LIVROS =====\n" +
                                "1 - Buscar livro pelo título (API)\n" +
                                "2 - Listar livros cadastrados\n" +
                                "3 - Listar autores\n" +
                                "4 - Listar livros por ano\n" +
                                "5 - Listar livros por idioma\n" +
                                "6 - Sair\n" +
                                "Escolha uma opção:"
                );

                if (input == null) break; // Cancelar fecha o programa

                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        String titulo = JOptionPane.showInputDialog("Digite o título do livro:");
                        if (titulo != null && !titulo.trim().isEmpty()) {
                            Livro livro = gutendexService.buscarPorTitulo(titulo);
                            if (livro != null) {
                                JOptionPane.showMessageDialog(null, "Livro inserido: " + livro.getTitulo() + " | Autor: " + livro.getAutor());
                            } else {
                                JOptionPane.showMessageDialog(null, "Livro não encontrado na API.");
                            }
                        }
                        break;

                    case 2:
                        List<Livro> listaLivros = catalogoService.listarLivros();
                        if (listaLivros.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nenhum livro cadastrado.");
                        } else {
                            String livros = listaLivros.stream()
                                    .map(L -> L.getTitulo())
                                    .reduce("", (acc, t) -> acc + "- " + t + "\n");
                            JOptionPane.showMessageDialog(null, "Lista de livros:\n" + livros);
                        }
                        break;

                    case 3:
                        List<String> listaAutores = catalogoService.listarAutores();
                        if (listaAutores.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Nenhum autor cadastrado.");
                        } else {
                            String autores = listaAutores.stream()
                                    .reduce("", (acc, t) -> acc + "- " + t + "\n");
                            JOptionPane.showMessageDialog(null, "Lista de autores:\n" + autores);
                        }
                        break;

                    case 4:
                        String anoInput = JOptionPane.showInputDialog("Digite o ano:");
                        if (anoInput != null && !anoInput.trim().isEmpty()) {
                            int ano = Integer.parseInt(anoInput);
                            List<Livro> livrosAno = catalogoService.listarAutoresPorAno(ano);
                            if (livrosAno.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Nenhum livro encontrado no ano " + ano);
                            } else {
                                StringBuilder sb = new StringBuilder();
                                for (Livro l : livrosAno) sb.append("- ").append(l.getTitulo()).append("\n");
                                JOptionPane.showMessageDialog(null, "Livros do ano " + ano + ":\n" + sb.toString());
                            }
                        }
                        break;

                    case 5:
                        String idioma = JOptionPane.showInputDialog("Digite o idioma (ex: en, pt, es, fr):");
                        if (idioma != null && !idioma.trim().isEmpty()) {
                            List<Livro> livrosIdioma = catalogoService.listarLivrosPorIdioma(idioma);
                            if (livrosIdioma.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Nenhum livro encontrado no idioma " + idioma);
                            } else {
                                StringBuilder sb = new StringBuilder();
                                for (Livro l : livrosIdioma) sb.append("- ").append(l.getTitulo()).append("\n");
                                JOptionPane.showMessageDialog(null, "Livros no idioma " + idioma + ":\n" + sb.toString());
                            }
                        }
                        break;

                    case 6:
                        JOptionPane.showMessageDialog(null, "Saindo...");
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida!");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite apenas números válidos!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro: " + e.getMessage());
            }
        }
    }
}
