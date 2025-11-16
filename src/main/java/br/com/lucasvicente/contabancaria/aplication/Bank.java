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
        boolean verificator;
        BigDecimal money;
        int comparator, userId = -1, selection;
        byte menuP;
        String actualCpf, confirmPassword, actualPassword, user = "", username, cpf, password, keyPix;
        Person actualUser = new Person();

        List<Person> users = new ArrayList<>();

        do {
        menuP = 0;
        verificator = false;
        System.out.println("Bem vindo ao LV Bank, Possui Cadastro?\n\n1)Sim\n2)Nao\n3)Sair");
        selection = read.nextInt();


        if (selection == 2) {


            System.out.println("Digite seu nome:");
            read.nextLine();
            username = read.nextLine();

            System.out.println("Digite seu cpf:(Obs: somente números) ");
            cpf = read.next();

            do {
                System.out.println("Digite uma senha:");
                password = read.next();

                System.out.println("Confirme a senha:");
                confirmPassword = read.next();

                if (confirmPassword.equals(password)) {
                    verificator = true;
                    Person newUser = new Person(username, cpf, password);
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
                actualUser = users.get(i);

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
                actualUser = users.get(userId);
                actualPassword = read.next();
                if (actualUser.getPassword().equals(actualPassword)) {
                    verificator = true;
                } else {
                    verificator = false;
                    System.out.println("Senha invalida");
                }
            }
            while (verificator == true && menuP != 5) {
                System.out.printf("Bem vindo(a) %s%n%nEsta é a tela principal do LV Bank, selecione uma das opções abaixo:%n", actualUser.getFullName());
                System.out.println(
                                "1 - verificar saldo\n" +
                                "2 - efetuar saque\n" +
                                "3 - Área pix\n" +
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
                        System.out.println("Selecione uma das opções abaixo:");
                        System.out.println(
                                "1)Efetuar Pix\n" +
                                "2)Cadastrar chave pix\n"
                        );
                        menuP = read.nextByte();
                        switch (menuP) {
                            case 1:
                                System.out.println("Informe a chave pix do destinatário:");
                                break;
                            case 2:
                                System.out.print("Chaves cadrastadas: ");
                                if(actualUser.getKeyPix().size() == 0) {
                                    System.out.println(0);
                                } else {
                                    System.out.println(actualUser.getKeyPix().size());
                                    for (int i = 0; i < actualUser.getKeyPix().size(); i++){
                                        System.out.printf("Chave %d: %s%n",i+1,actualUser.getKeyPix().get(i));
                                    }
                                }
                                System.out.println("Informe a chave pix para cadastrar:");
                                keyPix = read.next();
                                actualUser.addKeyPix(keyPix);
                                System.out.println("Chave cadastrada com sucesso!");
                                break;
                        }
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
        } while (selection != 3);
            read.close();
            System.out.println("Obrigado por usar o LV Bank!");

    }

}