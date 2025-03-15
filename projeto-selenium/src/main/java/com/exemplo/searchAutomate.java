package com.exemplo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class searchAutomate {
    public static void main(String[] args) {
        // Caminho do arquivo JSON
        String jsonFilePath = "/home/lucas-qa/Documentos/auto/projeto-selenium/src/main/resources/proxies.json";

        while (true) {
            // Carregar a lista de proxies
            List<String> proxies = loadProxies(jsonFilePath);

            if (proxies.isEmpty()) {
                System.out.println("Nenhum proxy encontrado no arquivo JSON.");
                return;
            }

            // Escolher um proxy aleatório
            String proxy = getRandomProxy(proxies);
            System.out.println("Usando proxy: " + proxy);

            // Configurar ChromeOptions com proxy
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--proxy-server=" + proxy);

            // Evitar detecção de automação
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);
            options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);

            // Inicializar WebDriver
            WebDriver driver = new ChromeDriver(options);

            try {
                driver.get("https://www.google.com/");
                Thread.sleep(700);

                driver.get("https://teste-ads-phi.vercel.app/");
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                driver.manage().deleteAllCookies();
            }
        }
    }

    // Método para carregar a lista de proxies do JSON
    public static List<String> loadProxies(String filePath) {
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

    // Método para escolher um proxy aleatório
    public static String getRandomProxy(List<String> proxies) {
        Random rand = new Random();
        return proxies.get(rand.nextInt(proxies.size()));
    }
}
