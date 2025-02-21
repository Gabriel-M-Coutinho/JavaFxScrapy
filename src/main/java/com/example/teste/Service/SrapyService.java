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
        extractDadosEstabelecimento();

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

    private void extractDadosEstabelecimento() {
        WebElement container = driver.findElement(By.xpath("/html/body/div/div[2]/div/div/main/div/div[2]/div[5]"));
        List<WebElement> elements = container.findElements(By.cssSelector("span"));

        for (WebElement element : elements) {
            System.out.println(element.getText());
        }
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