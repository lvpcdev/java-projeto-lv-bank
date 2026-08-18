package br.com.lucasvicente.contabancaria.aplication;

import br.com.lucasvicente.contabancaria.controller.AccountController;
import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import br.com.lucasvicente.contabancaria.entites.Account;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Locale;

public class ServidorBanco {

    private static  final AccountController accountController = new AccountController();

    public static void main(String[] args) throws Exception {
        DatabaseConnection.startDataBase();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/saldo", exchange -> {
            try {
                String query = exchange.getRequestURI().getQuery();
                int id = Integer.parseInt(query.split("=")[1]);

                Account conta = accountController.findById(id);
                String resposta = String.format(Locale.US,"{\"saldo\": %.2f}", conta.getBalance());

                responderJson(exchange, resposta);
            }catch (Exception e) {
                try {
                    String erro = "{\"erro\": \"Não foi possível processar a requisição\"}";
                    exchange.sendResponseHeaders(400, erro.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(erro.getBytes());
                    os.close();
                } catch (IOException ex) {
                    System.out.println("Erro ao enviar resposta de erro: " + ex.getMessage());
                }
            }
        });

        server.start();
        System.out.println("Servidor rodando na porta 8080");
    }

    private static  void responderJson(com.sun.net.httpserver.HttpExchange exchange, String resposta) throws Exception {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, resposta.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes());
        os.close();
    }
}
