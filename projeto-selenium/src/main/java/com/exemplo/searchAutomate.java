package com.exemplo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class searchAutomate {
    public static void main(String[] args) {

        // Configurar ChromeOptions para evitar detecção
        ChromeOptions options = new ChromeOptions();

        // Definir um User-Agent de um navegador real
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");

        // Remover a flag de automação do Chrome
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Iniciar o navegador sem a mensagem "Chrome está sendo controlado automaticamente"
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        // Remover a variável que indica automação para sites
        options.setExperimentalOption("useAutomationExtension", false);

        // Inicializar o WebDriver com as configurações
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.google.com");

            Thread.sleep((long) (Math.random() * 2000 + 1000));

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("Pix Mídia");
            Thread.sleep((long) (Math.random() * 2000 + 1000));
            searchBox.sendKeys(Keys.RETURN);

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
