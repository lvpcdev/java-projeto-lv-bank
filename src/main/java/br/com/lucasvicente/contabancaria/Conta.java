package br.com.lucasvicente.contabancaria;

import java.math.BigDecimal;
import java.util.Scanner;
public class Conta {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        String user, password;
        BigDecimal balance = new BigDecimal("1.112");
        byte menuP;

        System.out.println("Bem vindo ao LV Bank, Por favor efetue o login abaixo:");
        System.out.println("Digite o nome do usuario:");
        user = read.next();
        System.out.println("Digite a senha:");
        password = read.next();


        do {
            System.out.println("Está é a tela principal do LV Bank, selecione uma das opções abaixo:");
            System.out.println(
                    "1 - verificar saldo\n" +
                    "2 - efetuar saque\n" +
                    "3 - realizar pix\n" +
                    "4 - realizar tranferencia bancaria\n" +
                    "5 - Sair"
            );
            menuP = read.nextByte();
            read.nextLine();

            switch (menuP) {
                case 1:
                    System.out.printf("Saldo disponível: %.2f%n", balance);

            }

        } while (menuP != 5);


    }
}
