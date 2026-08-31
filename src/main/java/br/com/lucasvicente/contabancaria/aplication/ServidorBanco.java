package br.com.lucasvicente.contabancaria.aplication;

import br.com.lucasvicente.contabancaria.controller.AccountController;
import br.com.lucasvicente.contabancaria.controller.PersonController;
import br.com.lucasvicente.contabancaria.controller.PixKeyController;
import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class ServidorBanco {

    public static void main(String[] args) throws Exception {
        DatabaseConnection.startDataBase();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/accounts", new AccountController());
        server.createContext("/people", new PersonController());
        server.createContext("/pixkeys", new PixKeyController());

        server.start();
        System.out.println("Servidor rodando na porta 8080");
    }
}