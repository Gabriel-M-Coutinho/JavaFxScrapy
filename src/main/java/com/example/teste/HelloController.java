package com.example.teste;

import com.example.teste.Model.CNPJ;
import com.example.teste.Service.FileProcessorService;
import com.example.teste.Service.SrapyService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    @FXML
    private Label textArquivoSelecionado;
    @FXML
    private VBox containerProgressBar;
    @FXML
    private ProgressBar progressBar;

    private List<String> cnpjList;
    private  List<CNPJ> response;
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
                    textArquivoSelecionado.setText(selectedFile.getName());
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
    public void Scrapy(ActionEvent event) {
        // Verifica se a lista de CNPJs está vazia
        if (cnpjsExtraidos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum arquivo selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um arquivo antes de continuar.");
            alert.showAndWait();
            return;
        }

        System.out.println("Iniciando processamento...");

        // Exibe o container da ProgressBar
        containerProgressBar.setVisible(true);

        // Define o tamanho da ProgressBar com base no número de CNPJs
        progressBar.setPrefWidth(cnpjsExtraidos.size() * 30);

        // Cria uma Task para processar os CNPJs
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    for (int i = 0; i < cnpjsExtraidos.size(); i++) {
                        String cnpj = cnpjsExtraidos.get(i);

                        // Simula o processamento do CNPJ
                        System.out.println("Processando CNPJ: " + cnpj);
                         CNPJ cnpjData = srapyService.getData(cnpj);

                        // Atualiza o progresso
                        double progresso = (double) (i + 1) / cnpjsExtraidos.size();
                        Platform.runLater(() -> {
                            progressBar.setProgress(progresso);
                        });

                        // Simula um delay (opcional)
                        Thread.sleep(500); // 500ms de delay
                    }
                } catch (Exception e) {
                    // Captura exceções e exibe uma mensagem de erro
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro no processamento");
                        alert.setHeaderText(null);
                        alert.setContentText("Ocorreu um erro durante o processamento: " + e.getMessage());
                        alert.showAndWait();
                    });
                }
                return null;
            }
        };

        // Quando a Task terminar, exibe uma mensagem de conclusão
        task.setOnSucceeded(e -> {
            System.out.println("Processamento concluído!");
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Processamento concluído");
                alert.setHeaderText(null);
                alert.setContentText("Todos os CNPJs foram processados com sucesso!");
                alert.showAndWait();


                containerProgressBar.setVisible(false);
            });
        });

        // Inicia a Task em uma nova thread
        new Thread(task).start();
    }




    public void extractFiliais(){

        if(cnpjList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nenhum arquivo selecionado");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um arquivo antes de continuar.");
            alert.showAndWait();
        }else{

            for (String cnpj : cnpjList){
                srapyService.getFiliais(cnpj);
            }

        }

    }


}
