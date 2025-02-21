package com.example.teste.Service;

import com.example.teste.Model.CNPJ;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class SrapyService {

    private WebDriver driver;
    private CNPJ cnpj;

    public SrapyService() {
        this.cnpj = new CNPJ();
    }

    public void getData(String cnpjNumber) {
        setupDriver();
        navigateToCNPJPage(cnpjNumber);

        extractCadastroEmpresarial();
        extractEstabelecimento();
        closeDriver();
    }

    private void setupDriver() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    private void navigateToCNPJPage(String cnpjNumber) {
        driver.get("https://cnpja.com/office/" + cnpjNumber);
        cnpj.setCNPJ(cnpjNumber);
    }

    private void extractCadastroEmpresarial() {
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


    private void extractEstabelecimento() {
        WebElement container = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/main/div/div[2]/div[5]"));
        List<WebElement> elements = container.findElements(By.cssSelector("span"));


        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);
            String text = element.getText();
            System.out.println("index" + i + " :" + text);

            // Usa um switch para mapear os dados
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
                    cnpj.setTelefone(text); // Telefone
                    break;
                case 15:
                    cnpj.setEmail(text); // E-mail
                    break;
                case 17:
                    cnpj.setMunicipioIBGE(text); // Município IBGE
                    break;
                case 19:
                    cnpj.setEndereco(text); // Endereço (Rua)
                    break;
                case 21:
                    cnpj.setNumero(text); // Número
                    break;
                case 24:
                    cnpj.setBairro(text); // Bairro
                    break;
                case 26:
                    cnpj.setMunicipioIBGE(text); // Município (Cidade)
                    break;
                case 28:
                    cnpj.setEstado(text); // Estado (UF)
                    break;
                case 30:
                    cnpj.setCEP(text); // CEP
                    break;
                default:
                    // Índices que não são mapeados podem ser ignorados
                    break;
            }
        }

        // Exibe o objeto CNPJ preenchido
        System.out.println(cnpj.toString());
    }


    private void extractCnae(){}

    private void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public CNPJ getCnpj() {
        return cnpj;
    }
}