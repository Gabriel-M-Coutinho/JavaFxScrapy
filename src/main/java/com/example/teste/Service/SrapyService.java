package com.example.teste.Service;

import com.example.teste.Model.CNPJ;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.http.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SrapyService {

    private WebDriver driver;


    public SrapyService() {

    }

    public List<CNPJ> getData(List<String> cnpjs){
        List<CNPJ> response = new ArrayList<>();
        for(String cnpj : cnpjs){
            CNPJ obj = getData(cnpj);
            response.add(obj);

        }

        return response;

    }

    public CNPJ getData(String cnpjNumber) {
        WebDriver driver = null;
        CNPJ cnpj = new CNPJ();
        cnpj.setCNPJ(cnpjNumber);

        try {
            // Configuração do driver Firefox em modo headless
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless"); // Executa em modo headless
            options.addArguments("--disable-gpu"); // Desabilita a GPU (recomendado para headless)
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(options);


            driver.get("https://cnpja.com/office/" + cnpjNumber);


            extractCadastroEmpresarial(driver, cnpj);
            extractEstabelecimento(driver, cnpj);
            extractCnae(driver, cnpj);

        } catch (Exception e) {
            System.err.println("Erro ao processar CNPJ " + cnpjNumber + ": " + e.getMessage());

        } finally {
            if (driver != null) {
                driver.quit(); //
            }
        }

        return cnpj;
    }
    private void extractCadastroEmpresarial(WebDriver driver ,CNPJ cnpj) {
        try{
            WebElement container = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/main/div/div[2]/div[3]"));
            List<WebElement> elementList = container.findElements(By.cssSelector("div.flex.items-center span"));
            for (int i = 0; i < elementList.size(); i++) {
                WebElement element = elementList.get(i);

                switch (i) {
                    case 1:
                        cnpj.setRazao(element.getText());
                        break;
                    case 3:
                        cnpj.setPorte(element.getText());
                        break;
                    case 5:
                        cnpj.setCapitalSocial(element.getText());
                        break;
                    case 7:
                        cnpj.setNaturezaJuridica(element.getText() + elementList.get(i + 1).getText() + elementList.get(i + 2).getText());
                        break;
                    case 11:
                        cnpj.setSimplesNacional(element.getText());
                        break;
                    case 13:
                        cnpj.setMei(element.getText());
                        break;
                }
            }
        }catch (Exception e){


        }



    }

    private void extractEstabelecimento(WebDriver driver,CNPJ cnpj) {
        WebElement container = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/main/div/div[2]/div[5]"));
        List<WebElement> elements = container.findElements(By.cssSelector("span"));


        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            String text = element.getText();



            switch (i) {
                case 1:
                    cnpj.setCNPJ(text); // CNPJ
                    break;
                case 3:
                    cnpj.setTipo(text); // Tipo (Matriz/Filial)
                    break;
                case 5:
                    cnpj.setDataAbertura(text); // Data de Abertura
                    break;
                case 7:
                    cnpj.setNomeFantasia(text); // Nome Fantasia
                    break;
                case 9:
                    cnpj.setSituacaoCadastral(text); // Situação Cadastral
                    break;
                case 11:
                    cnpj.setSituacaoDesde(text); // Situação Desde
                    break;
                case 13:
                    cnpj.setTelefone(text); // Telefone 1
                    break;
                case 16:
                    cnpj.setEmail(text); // E-mail
                    break;
                case 18:
                    cnpj.setMunicipioIBGE(text); // Município IBGE
                    break;
                case 20:
                    cnpj.setEndereco(text); // Endereço (Rua/Avenida)
                    break;
                case 22:
                    cnpj.setNumero(text); // Número
                    break;
                case 23:
                    cnpj.setBairro(text); // Bairro
                    break;
                case 25:
                    cnpj.setMunicipioIBGE(text); // Cidade
                    break;
                case 27:
                    cnpj.setEstado(text); // Estado
                    break;
                case 29:
                    cnpj.setCEP(text); // CEP
                    break;
                default:
                    // Ignorar outros índices
                    break;
            }
        }



    }

    private void extractCnae(WebDriver driver,CNPJ cnpj) {
        WebElement container = driver.findElement(By.cssSelector("table.table.table-compact"));
        List<WebElement> elementList = container.findElements(By.cssSelector("tbody tr td span"));
        List<String> listCnaeSecundario = new ArrayList<>();

        for (int i = 1; i < elementList.size(); i = i + 2) {
            if (i == 1) {// Acessa o primeiro par de elementos
                cnpj.setCnaePrincipal(elementList.get(i).getText() + " " + elementList.get(i+1).getText());
            } else if (i > 1) {

                if (i + 1 < elementList.size()) {
                    listCnaeSecundario.add(elementList.get(i).getText() + elementList.get(i + 1).getText());
                } else {

                    listCnaeSecundario.add(elementList.get(i).getText());
                }
            }
        }

        cnpj.setCnaeSecundario(listCnaeSecundario);

    }

    public List<String> getFiliais(String cnpjProcurado) {
        List<String> cnpjsFiliais = new ArrayList<>();
        String jsonData = fetchDataFromApi(cnpjProcurado);

        if (jsonData == null) {
            return cnpjsFiliais;
        }


        String cnpjBase = cnpjProcurado.length() >= 8 ? cnpjProcurado.substring(0, 8) : cnpjProcurado;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);

            JsonNode nodesArray = rootNode.path("nodes");
            if (!nodesArray.isArray() || nodesArray.size() < 3) {
                System.err.println("Erro: Estrutura JSON inesperada.");
                return cnpjsFiliais;
            }

            JsonNode filiaisNode = nodesArray.get(2).path("data");

            if (filiaisNode.isArray()) {
                for (JsonNode filial : filiaisNode) {
                    if (filial.isTextual() && filial.asText().contains(cnpjBase)) {
                        cnpjsFiliais.add(filial.asText());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
        }

        return cnpjsFiliais;
    }

    private String fetchDataFromApi(String cnpjMatriz) {
        String url = "https://cnpja.com/company/" + cnpjMatriz + "/__data.json?x-sveltekit-invalidated=001";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar dados da API: " + e.getMessage());
            return null;
        }
    }


}