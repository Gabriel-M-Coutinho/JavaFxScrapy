package com.example.teste.Service;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class APICnpjBase {
    private static final String API_URL = "https://cnpja.com/company/";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getCnpjs(String cnpjBase) {

        List<String> resultados = new ArrayList<>();

        try {
            // Faz a requisição HTTP
            String url = API_URL + cnpjBase + "/__data.json?x-sveltekit-invalidated=001";
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Erro ao buscar dados: " + response.code());
            }

            // Processa o JSON
            String jsonResponse = response.body().string();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Filtra os dados
            JsonNode nodes = rootNode.path("nodes").get(2).path("data");
            for (JsonNode element : nodes) {
                if (element.isTextual() && element.asText().contains(cnpjBase)) {
                    resultados.add(element.asText());
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao processar os dados: " + e.getMessage());
        }

        return resultados;
    }
}
