package com.exemplo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class searchAutomate {
    private static final int NUM_INSTANCES = 5;

    public static void main(String[] args) {
        String jsonFilePath = "/home/lucas-qa/Documentos/auto/projeto-selenium/src/main/resources/proxies.json";
        List<String> proxies = loadProxies(jsonFilePath);

        if (proxies.isEmpty()) {
            System.out.println("Nenhum proxy encontrado no arquivo JSON.");
            return;
        }

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NUM_INSTANCES; i++) {
            String proxy = getRandomProxy(proxies);
            Thread thread = new Thread(() -> runInstance(proxy));
            threads.add(thread);
            thread.start();
        }

        // Aguarda todas as threads finalizarem (Opcional)
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void runInstance(String proxy) {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--proxy-server=http://" + proxy);
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(options);
        System.out.println("Iniciando instância com proxy: " + proxy);

        try {
            while (true) {
                try {
                    driver.navigate().to("https://www.google.com/");
                    Thread.sleep(500);

                    driver.navigate().to("https://teste-ads-phi.vercel.app/");
                    Thread.sleep(500);
                    driver.manage().deleteAllCookies();

                    driver.navigate().to("https://industriainovadora.com.br/");
                    Thread.sleep(500);
                    driver.manage().deleteAllCookies();

                    driver.navigate().to("https://rsinovador.com.br/");
                    Thread.sleep(500);

                    //WebElement searchBox = driver.findElement(By.name("q"));
                    //searchBox.sendKeys("g1");
                    //Thread.sleep(100);
                    //searchBox.sendKeys(Keys.RETURN);

                    //WebElement firstResult = driver.findElement(By.cssSelector("h3"));
                    //firstResult.click();
                    driver.manage().deleteAllCookies();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static List<String> loadProxies(String filePath) {
        List<String> proxies = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray proxyArray = jsonObject.getJSONArray("proxies");

            for (int i = 0; i < proxyArray.length(); i++) {
                proxies.add(proxyArray.getString(i));
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
        return proxies;
    }

    private static String getRandomProxy(List<String> proxies) {
        Random rand = new Random();
        return proxies.get(rand.nextInt(proxies.size()));
    }
}
