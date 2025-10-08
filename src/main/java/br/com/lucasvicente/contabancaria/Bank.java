package br.com.lucasvicente.contabancaria;

import java.math.BigDecimal;
import java.util.Scanner;

public class Bank {
    public static void main(String[] args)  {


        Scanner read = new Scanner(System.in);
        String user, password;
        BigDecimal balance = new BigDecimal("1112.00");
        BigDecimal money;
        int comparator;
        byte menuP;

        do {
            System.out.println("Bem vindo ao LV Bank, Por favor efetue o login abaixo:");
            System.out.println("Digite o nome do usuario:");
            user = read.next();
            System.out.println("Digite a senha:");
            password = read.next();


            do {
                System.out.printf("Bem vindo(a) %s%n%nEsta é a tela principal do LV Bank, selecione uma das opções abaixo:%n", user);
                System.out.println(
                        "1 - verificar saldo\n" +
                                "2 - efetuar saque\n" +
                                "3 - realizar pix\n" +
                                "4 - adicionar saldo\n" +
                                "5 - Sair"
                );
                menuP = read.nextByte();
                read.nextLine();

                switch (menuP) {
                    case 1:
                        System.out.printf("Saldo disponível: %.2f%n", balance);
                        break;
                    case 2:
                        System.out.println("informe o valor que deseja  sacar:");
                        money = read.nextBigDecimal();
                        comparator = money.compareTo(balance);
                        if (comparator > 0) {
                            System.out.println("Valor de saque maior do do que valor disponivel");

                        } else {
                            balance = balance.subtract(money);
                            System.out.println("Saque efetuado!");
                        }
                        break;
                    case 3:
                        System.out.println("Informe a chave pix para realizar a transferencia:");
                        // Codigo a ser implementado
                        break;
                    case 4:
                        System.out.println("Informe o valor que será adicionado na conta:");
                        money = read.nextBigDecimal();
                        balance = balance.add(money);
                        System.out.println("Valor adicionado!");
                        break;
                    case 5:
                        System.out.println("Saindo do sistema");
                        break;
                    default:
                        System.out.println("Error: Valor digitado inválido");
                        break;
                }

            } while (menuP != 6 && menuP != 5);
        } while(menuP == 5);
        System.out.println("Obrigado por usar o LV Bank!");
        read.close();
    }
}
