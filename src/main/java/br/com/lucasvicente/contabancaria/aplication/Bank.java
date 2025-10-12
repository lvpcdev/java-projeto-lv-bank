package br.com.lucasvicente.contabancaria.aplication;

import br.com.lucasvicente.contabancaria.entites.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bank {
    public static void main(String[] args) {

        Scanner read = new Scanner(System.in);

        BigDecimal balance = new BigDecimal("1112.00");
        boolean verificator = false;
        BigDecimal money;
        int comparator, userId = -1, selection;
        byte menuP = 0;
        String actualCpf, confirmPassword, actualPassword, user = "";

        List<Person> users = new ArrayList<>();

        do {

        System.out.println("Bem vindo ao LV Bank, Possui Cadastro?\n\n1)Sim\n2)Nao");
        selection = read.nextInt();


        if (selection == 2) {

            Person newUser = new Person();
            System.out.println("Digite seu nome:");
            read.nextLine();
            newUser.setFullName(read.nextLine());

            System.out.println("Digite seu cpf:(Obs: somente números) ");
            newUser.setCpf(read.next());

            do {
                System.out.println("Digite uma senha:");
                newUser.setPassword(read.next());

                System.out.println("Confirme a senha:");
                confirmPassword = read.next();

                if (confirmPassword.equals(newUser.getPassword())) {
                    verificator = true;
                    users.add(newUser);
                } else {
                    System.out.println("Tente novamente");
                }

            } while (verificator == false);
            verificator = false;


        } else if (selection == 1) {

            System.out.println("Digite o seu cpf:");
            actualCpf = read.next();
            for (int i = 0; i < users.size() && verificator == false; i++) {
                Person actualUser = users.get(i);

                if (actualUser.getCpf().equals(actualCpf)) {
                    verificator = true;
                    userId = i;
                    user = actualUser.getFullName();
                }
            }

            if (verificator == false) {
                System.out.println("Usuário não cadastrado");

            } else {
                System.out.println("Digite a senha:");
                Person actualUser = users.get(userId);
                actualPassword = read.next();
                if (actualUser.getPassword().equals(actualPassword)) {
                    verificator = true;
                } else {
                    verificator = false;
                    System.out.println("Senha invalida");
                }
            }
            while (verificator == true && menuP != 5) {
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

            }
        }
        } while (menuP != 5);
            read.close();
            System.out.println("Obrigado por usar o LV Bank!");

    }

}