package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.dto.requests.PixKeyRequestDTO;
import br.com.lucasvicente.contabancaria.dto.responses.PixKeyResponseDTO;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.PixKey;
import br.com.lucasvicente.contabancaria.service.PixKeyService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PixKeyController implements HttpHandler {

    private final PixKeyService pixKeyService = new PixKeyService();

    private final Gson gson = new Gson();

    public void findAll(HttpExchange exchange) throws IOException {
        List<PixKeyResponseDTO> pixKeys = pixKeyService.findAll();
        String json = toJsonList(pixKeys);
        responderJson(exchange, json, 200);
    }

    public void findById(HttpExchange exchange, Long id) throws IOException {
        PixKeyResponseDTO pixKey = pixKeyService.findById(id);
        String json = toJson(pixKey);
        responderJson(exchange, json, 200);
    }

    public void insert (HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        PixKeyRequestDTO dto = parsePixKeyRequestDTO(body);
        PixKeyResponseDTO createdPixKey = pixKeyService.insert(dto);
        responderJson(exchange, toJson(createdPixKey), 201);
    }

    public void update (HttpExchange exchange, Long id) throws  IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        PixKeyRequestDTO dto = parsePixKeyRequestDTO(body);
        PixKeyResponseDTO updatedPixKey = pixKeyService.update(id, dto);
        responderJson(exchange, toJson(updatedPixKey), 200);
    }

    public void delete(HttpExchange exchange, Long id) throws IOException {
        pixKeyService.delete(id);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

    public void findAllByAccountId(HttpExchange exchange, Long id) throws IOException {
        List<PixKeyResponseDTO> pixKeys = pixKeyService.findAllByAccountId(id);
        String json = toJsonList(pixKeys);
        responderJson(exchange, json, 200);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String metodo = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            switch (metodo) {
                case "GET" -> {
                    String query = exchange.getRequestURI().getQuery();

                    if (query != null && query.startsWith("accountId=")) {
                        Long accountId = Long.parseLong(query.split("=")[1]);
                        findAllByAccountId(exchange, accountId);
                    } else if (path.matches("/pixkeys/\\d+")) {
                        findById(exchange, extrairId(path));
                    } else {
                        findAll(exchange);
                    }
                }
                case "POST" -> insert(exchange);
                case "PUT" -> update(exchange, extrairId(path));
                case "DELETE" -> delete(exchange, extrairId(path));
                default -> responderJson(exchange, "{\"erro\": \"Método não suportado\"}", 400);
            }
        } catch (Exception e) {
            responderJson(exchange, "{\"erro\": \"" + e.getMessage() + "\"}", 400);
        }
    }

    private Long extrairId(String path) {
        String[] parts = path.split("/");
        return Long.parseLong(parts[2]);
    }

    private String toJson(PixKeyResponseDTO dto) {
        return gson.toJson(dto);
    }

    private String toJsonList(List<PixKeyResponseDTO> list) {
        return gson.toJson(list);
    }

    private PixKeyRequestDTO parsePixKeyRequestDTO(String body) {
        return gson.fromJson(body, PixKeyRequestDTO.class);
    }

    private void responderJson(HttpExchange exchange, String resposta, Integer statusCode) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, resposta.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes());
        os.close();
    }
}
