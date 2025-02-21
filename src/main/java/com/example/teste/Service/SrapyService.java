package com.example.teste.Service;

import com.example.teste.Model.CNPJ;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
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

        //extractCadastroEmpresarial();
        //extractEstabelecimento();
        extractCnae();
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


            switch (i) {
                case 1:
                    cnpj.setCNPJ(text);
                    break;
                case 3:
                    cnpj.setTipo(text);
                    break;
                case 5:
                    cnpj.setDataAbertura(text);
                    break;
                case 7:
                    cnpj.setNomeFantasia(text);
                    break;
                case 9:
                    cnpj.setSituacaoCadastral(text);
                    break;
                case 11:
                    cnpj.setSituacaoDesde(text);
                    break;
                case 13:
                    cnpj.setTelefone(text);
                    break;
                case 15:
                    cnpj.setEmail(text);
                    break;
                case 17:
                    cnpj.setMunicipioIBGE(text);
                    break;
                case 19:
                    cnpj.setEndereco(text);
                    break;
                case 21:
                    cnpj.setNumero(text);
                    break;
                case 24:
                    cnpj.setBairro(text);
                    break;
                case 26:
                    cnpj.setMunicipioIBGE(text);
                    break;
                case 28:
                    cnpj.setEstado(text);
                    break;
                case 30:
                    cnpj.setCEP(text);
                    break;
                default:

                    break;
            }
        }


        System.out.println(cnpj.toString());
    }


    private void extractCnae() {
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
        System.out.println(cnpj);
    }

    private void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public CNPJ getCnpj() {
        return cnpj;
    }
}