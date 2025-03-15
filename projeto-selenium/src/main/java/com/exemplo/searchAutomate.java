package com.exemplo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class searchAutomate {
    public static void main(String[] args) {
        // Configurar ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        // Inicializar o WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.google.com");

            // Adicionar um pequeno delay aleatório
            Thread.sleep((long) (Math.random() * 2000 + 1000));

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("TV corporativa");
            Thread.sleep((long) (Math.random() * 2000 + 1000));
            // Capturar todas as sugestões do preenchimento automático
            List<WebElement> suggestions = driver.findElements(By.cssSelector("li.sbct"));
            // Verificar se há sugestões disponíveis
            if (!((List<?>) suggestions).isEmpty()) {
                // Clicar na primeira sugestão (ou modificar para escolher outra)
                suggestions.get(1).click();
            } else {
                System.out.println("Nenhuma sugestão encontrada!");
                searchBox.sendKeys(Keys.RETURN); // Caso não haja sugestões, faz a pesquisa normal
            }
            searchBox.sendKeys(Keys.RETURN);

            // Clicar no primeiro resultado da pesquisa
            WebElement firstResult = driver.findElement(By.cssSelector("h3"));
            firstResult.click();

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
