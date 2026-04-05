package br.com.lucasvicente.contabancaria.aplication;

import br.com.lucasvicente.contabancaria.controller.PersonController;
import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Bank;
import br.com.lucasvicente.contabancaria.entites.Person;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {



    private static final PersonController personController = new PersonController();

    public static void main(String[] args) throws SQLException {
        DatabaseConnection.startDataBase();
        org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();


        Bank bank = new Bank("lv-bank");

        Scanner sc = new Scanner(System.in);

        boolean verificator;
        int comparator, selection;
        byte menuP;
        String actualCpf, confirmPassword, actualPassword, username, cpf, password, keyPix;
        Account actualAccount = new Account();

        do {
        menuP = 0;
        verificator = false;
        System.out.println("Bem vindo ao LV Bank, Possui Cadastro?\n\n1)Sim\n2)Nao\n3)Sair");
        selection = sc.nextInt();


        if (selection == 2) {


            System.out.println("Digite seu nome:");
            sc.nextLine();
            username = sc.nextLine();

            System.out.println("Digite seu cpf:(Obs: somente números) ");
            cpf = sc.next();

            do {
                System.out.println("Digite uma senha:");
                password = sc.next();

                System.out.println("Confirme a senha:");
                confirmPassword = sc.next();

                if (confirmPassword.equals(password)) {
                    verificator = true;
                    Person newUser = new Person(null, username, cpf);
                    Account newAccount = new Account(bank,newUser,password, bank.getAccounts().size()+1, "0001");
                    personController.insert(username,cpf);
                    newUser.addAccount(newAccount);
                    bank.addAccount(newAccount);
                } else {
                    System.out.println("Tente novamente");
                }

            } while (verificator == false);

        } else if (selection == 1) {

            System.out.println("Digite o seu cpf:");
            actualCpf = sc.next();
            for (int i = 0; i < bank.getAccounts().size() && verificator == false; i++) {
                actualAccount = bank.getAccounts().get(i);

                if (actualAccount.getPerson().getCpf().equals(actualCpf)) {
                    verificator = true;
                }
            }

            if (verificator == false) {
                System.out.println("Usuário não cadastrado");

            } else {
                System.out.println("Digite a senha:");
                actualPassword = sc.next();
                if (actualAccount.getPassword().equals(actualPassword)) {
                    verificator = true;
                } else {
                    verificator = false;
                    System.out.println("Senha invalida");
                }
            }
            while (verificator == true && menuP != 5) {
                System.out.printf("Bem vindo(a) %s%n%nEsta é a tela principal do LV Bank, selecione uma das opções abaixo:%n", actualAccount.getPerson().getFullName());
                System.out.println(
                                "1 - verificar saldo\n" +
                                "2 - efetuar saque\n" +
                                "3 - Área pix\n" +
                                "4 - adicionar saldo\n" +
                                "5 - Sair"
                );
                menuP = sc.nextByte();
                sc.nextLine();

                switch (menuP) {
                    case 1:
                        System.out.printf("Saldo disponível: %.2f%n", actualAccount.getBalance());
                        break;
                    case 2:
                        System.out.println("informe o valor que deseja  sacar:");
                        BigDecimal money = sc.nextBigDecimal();
                        comparator = money.compareTo(actualAccount.getBalance());
                        if (comparator > 0) {
                            System.out.println("Valor de saque maior do do que valor disponivel");

                        } else {
                            actualAccount.withdraw(money);
                            System.out.println("Saque efetuado!");
                        }
                        break;
                    case 3:
                        System.out.println("Selecione uma das opções abaixo:");
                        System.out.println(
                                "1)Efetuar Pix\n" +
                                "2)Cadastrar chave pix\n"
                        );
                        menuP = sc.nextByte();
                        switch (menuP) {
                            case 1:
                                System.out.println("Informe a chave pix do destinatário:");
                                break;
                            case 2:
                                System.out.print("Chaves cadrastadas: ");
                                if(actualAccount.getPixKeys().size() == 0) {
                                    System.out.println(0);
                                } else {
                                    System.out.println(actualAccount.getPixKeys().size());
                                    for (int i = 0; i < actualAccount.getPixKeys().size(); i++){
                                        System.out.printf("Chave %d: %s%n",i+1,actualAccount.getPixKeys().get(i));
                                    }
                                }
                                System.out.println("Informe a chave pix para cadastrar:");
                                keyPix = sc.next();
                                actualAccount.addPixKey(keyPix);
                                System.out.println("Chave cadastrada com sucesso!");
                                break;
                        }
                        break;
                    case 4:
                        System.out.println("Informe o valor que será adicionado na conta:");
                        money = sc.nextBigDecimal();
                        actualAccount.depostit(money);
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
            sc.close();
            System.out.println("Obrigado por usar o LV Bank!");

    }

}