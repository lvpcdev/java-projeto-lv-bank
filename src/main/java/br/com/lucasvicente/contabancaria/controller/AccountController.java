package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.dto.requests.AccountRequestDTO;
import br.com.lucasvicente.contabancaria.dto.requests.AmountRequestDTO;
import br.com.lucasvicente.contabancaria.dto.responses.AccountResponseDTO;
import br.com.lucasvicente.contabancaria.exceptions.InsufficientBalanceException;
import br.com.lucasvicente.contabancaria.exceptions.NegativeValueException;
import br.com.lucasvicente.contabancaria.service.AccountService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

public class AccountController implements HttpHandler {
    private final AccountService accountService = new AccountService();
    private final Gson gson = new Gson();

    public void findAll(HttpExchange exchange) throws IOException {
        List<AccountResponseDTO> accounts = accountService.findAll();
        String json = toJsonList(accounts);
        responderJson(exchange, json, 200);
    }

    public void findById(HttpExchange exchange, Long id) throws IOException {
        AccountResponseDTO account = accountService.findById(id);
        String json = toJson(account);
        responderJson(exchange, json, 200);
    }

    public void insert (HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        AccountRequestDTO dto = parseAccountRequestDTO(body);
        AccountResponseDTO createdAccount = accountService.insert(dto);
        responderJson(exchange, toJson(createdAccount), 201);
    }

    public void update (HttpExchange exchange, Long id) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        AccountRequestDTO dto = parseAccountRequestDTO(body);
        AccountResponseDTO updatedAccount = accountService.update(id, dto);
        responderJson(exchange, toJson(updatedAccount), 200);
    }

    public void delete(HttpExchange exchange, Long id) throws IOException {
        accountService.delete(id);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

    public void deposit (HttpExchange exchange, Long id) throws IOException{
        String body = new String(exchange.getRequestBody().readAllBytes());
        AmountRequestDTO dto = gson.fromJson(body, AmountRequestDTO.class);

        try {
            accountService.deposit(id, dto.amount());

            AccountResponseDTO account = accountService.findById(id);
            String json = toJson(account);

            responderJson(exchange, json,200);
        } catch (NegativeValueException e) {
            responderJson(exchange, "{\"erro\": \"" + e.getMessage() + "\"}", 400);
        }
    }

    public void withdraw(HttpExchange exchange, Long id) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        AmountRequestDTO dto = gson.fromJson(body, AmountRequestDTO.class);

        try {
            accountService.withdraw(id, dto.amount());

            AccountResponseDTO account = accountService.findById(id);
            String json = toJson(account);

            responderJson(exchange, json,200);
        } catch (NegativeValueException | InsufficientBalanceException e) {
            responderJson(exchange, "{\"erro\": \"" + e.getMessage() + "\"}", 400);
        }
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String metodo = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            switch (metodo) {
                case "GET" -> {
                    if (path.matches("/accounts/\\d+")) {
                        findById(exchange, extrairId(path));
                    } else {
                        findAll(exchange);
                    }
                }
                case "POST" -> {
                    if (path.matches("/accounts/\\d+/deposit")) {
                        deposit(exchange, extrairId(path));
                    } else if (path.matches("/accounts/\\d+/withdraw")) {
                        withdraw(exchange, extrairId(path));
                    } else {
                        insert(exchange);
                    }
                }
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

    private String toJson(AccountResponseDTO dto) {
        return gson.toJson(dto);
    }

    private String toJsonList(List<AccountResponseDTO> list) {
        return gson.toJson(list);
    }

    private AccountRequestDTO parseAccountRequestDTO(String body) {
        return gson.fromJson(body, AccountRequestDTO.class);
    }

    private void responderJson(HttpExchange exchange, String resposta, int statusCode) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(statusCode, resposta.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes());
        os.close();
    }
}
