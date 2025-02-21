package com.example.teste;

import com.example.teste.Service.FileProcessorService;
import com.example.teste.Service.SrapyService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloController {
    @FXML
    private MenuItem menuItemCarregarCnpj;
    private final SrapyService srapyService = new SrapyService();
    private final FileProcessorService fileProcessorService = new FileProcessorService();



    private List<String> cnpjsExtraidos = new ArrayList<>();

    @FXML
    public void OpenExplorer(ActionEvent event) {
        // Criar o seletor de arquivos
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos Suportados", "*.txt", "*.csv", "*.xlsx"));

        // Obtém a referência para a janela principal
        Stage stage = (Stage) menuItemCarregarCnpj.getParentPopup().getOwnerWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("Arquivo selecionado: " + selectedFile.getAbsolutePath());

            try {
                // Processa o arquivo usando FileProcessorService
                List<String[]> data = fileProcessorService.readFile(selectedFile);

                if (!data.isEmpty()) {
                    String[] colunas = data.get(0); // Primeira linha contém os cabeçalhos

                    List<String> opcoesColunas = new ArrayList<>();
                    for (String coluna : colunas) {
                        opcoesColunas.add(coluna.trim()); // Remove espaços extras
                    }

                    // Cria um ChoiceDialog para selecionar a coluna do CNPJ
                    ChoiceDialog<String> dialog = new ChoiceDialog<>(opcoesColunas.get(0), opcoesColunas);
                    dialog.setTitle("Selecionar Coluna");
                    dialog.setHeaderText("Selecione a coluna que contém os CNPJs");
                    dialog.setContentText("Coluna:");

                    Optional<String> resultado = dialog.showAndWait();

                    // Processa a coluna escolhida pelo usuário
                    resultado.ifPresent(colunaSelecionada -> {
                        System.out.println("Coluna selecionada: " + colunaSelecionada);

                        int indiceColuna = opcoesColunas.indexOf(colunaSelecionada);
                        cnpjsExtraidos.clear(); // Limpa a lista antes de adicionar novos valores

                        for (int i = 1; i < data.size(); i++) { // Começa da segunda linha
                            String[] valores = data.get(i);
                            if (valores.length > indiceColuna) {
                                String cnpj = valores[indiceColuna].trim();
                                cnpjsExtraidos.add(cnpj); // Adiciona à lista
                            }
                        }

                        // Exibe os CNPJs extraídos no console (ou pode armazenar para outra finalidade)
                        System.out.println("Lista de CNPJs extraídos:");
                        cnpjsExtraidos.forEach(System.out::println);
                    });
                } else {
                    System.out.println("O arquivo está vazio.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao processar o arquivo.");
            }
        } else {
            System.out.println("Nenhum arquivo selecionado.");
        }
    }

    @FXML
    public void Scrapy(ActionEvent event){
        System.out.println("teste deu certo");
        srapyService.getData("34029082000119");
    }
    public List<String> getCnpjsExtraidos() {
        return cnpjsExtraidos;
    }
}
