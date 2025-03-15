package com.exemplo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class searchAutomate {

    public static void main(String[] args) {
        String jsonFilePath = "proxies.json"; // Caminho correto do arquivo JSON
        List<String> proxies = loadProxies(jsonFilePath);

        if (proxies.isEmpty()) {
            System.out.println("Nenhum proxy encontrado no arquivo JSON.");
            return;
        }

        String proxy = getRandomProxy(proxies);
        System.out.println("Usando proxy: " + proxy);

        // Configurar Chrome com o proxy selecionado
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--proxy-server=http://" + proxy);

        // Inicializar o WebDriver com as configurações
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.google.com/");

        // Exemplo de uso do Selenium e do proxy
        // Implemente as ações que deseja fazer no Selenium aqui
    }

    public static List<String> loadProxies(String filePath) {
        List<String> proxies = new ArrayList<>();
        try (InputStream is = searchAutomate.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is == null) {
                System.out.println("Arquivo JSON não encontrado: " + filePath);
                return proxies;
            }

            JSONTokener tokener = new JSONTokener(new InputStreamReader(is));
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

    public static String getRandomProxy(List<String> proxies) {
        Random rand = new Random();
        return proxies.get(rand.nextInt(proxies.size()));
    }
}
