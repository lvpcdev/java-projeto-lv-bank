package br.com.lucasvicente.contabancaria.aplication;

import br.com.lucasvicente.contabancaria.controller.AccountController;
import br.com.lucasvicente.contabancaria.controller.BankController;
import br.com.lucasvicente.contabancaria.controller.PersonController;
import br.com.lucasvicente.contabancaria.controller.PixKeyController;
import br.com.lucasvicente.contabancaria.database.DatabaseConnection;
import br.com.lucasvicente.contabancaria.entites.Account;
import br.com.lucasvicente.contabancaria.entites.Bank;
import br.com.lucasvicente.contabancaria.entites.Person;
import br.com.lucasvicente.contabancaria.entites.PixKey;
import br.com.lucasvicente.contabancaria.exceptions.InsufficientBalanceException;
import br.com.lucasvicente.contabancaria.exceptions.NegativeValueException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {



    private static final PersonController personController = new PersonController();
    private static final BankController bankController = new BankController();
    private static final AccountController accountController = new AccountController();
    private static final PixKeyController pixKeyController = new PixKeyController();

    public static void main(String[] args) throws SQLException {
        DatabaseConnection.startDataBase();
        org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();


        bankController.insert("lv-bank");
        Bank bank = bankController.findById(1);

        Scanner sc = new Scanner(System.in);

        boolean verificator;
        int selection;
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
                    Person newUser = personController.insert(username,cpf);
                    Account newAccount = accountController.insert(bank, newUser, password, accountController.findAll().size()+1, "0001");


                } else {
                    System.out.println("Tente novamente");
                }

            } while (verificator == false);

        } else if (selection == 1) {

            List<Account> accounts = accountController.findAll();

            System.out.println("Digite o seu cpf:");
            actualCpf = sc.next();
            for (int i = 1; i <= accounts.size() && verificator == false; i++) {
                actualAccount = accountController.findById(i);

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
                        System.out.printf("Saldo disponível: %.2f%n", accountController.findById(actualAccount.getId()).getBalance());
                        break;
                    case 2:
                        System.out.println("informe o valor que deseja  sacar:");
                        BigDecimal money = sc.nextBigDecimal();
                        try {

                            accountController.withdraw(actualAccount.getId(), money);
                        } catch (InsufficientBalanceException | NegativeValueException e) {
                            System.out.println("Erro: " + e.getMessage());
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
                                System.out.print("Informe a chave pix do destinatário: ");
                                String pixKey = sc.next();
                                System.out.print("Informe o valor a ser envido: ");
                                money = sc.nextBigDecimal();
                                //List<Account> accounts = accountController.findAll();
                                for (Account account : accounts) {
                                    List<PixKey> pixKeys = pixKeyController.findAllByAccountId(account.getId());
                                    for (PixKey pk : pixKeys) {
                                        if (pk.getKeyValue().equals(pixKey)) {
                                            try {
                                                accountController.withdraw(actualAccount.getId(), money);
                                                accountController.deposit(account.getId(), money);
                                                System.out.println("Pix enviado com sucesso!");
                                            } catch (InsufficientBalanceException | NegativeValueException e) {
                                                System.out.println("Erro: " + e.getMessage());
                                            }

                                            break;
                                        }
                                    }

                                }
                                break;
                            case 2:
                                System.out.print("Chaves cadrastadas: ");
                                List<PixKey> pixKeys = pixKeyController.findAllByAccountId(actualAccount.getId());
                                if(pixKeys.size() == 0) {
                                    System.out.println(0);
                                } else {
                                    System.out.println(pixKeys.size());
                                    for (int i = 0; i < pixKeys.size(); i++){
                                        System.out.printf("Chave %d: %s%n",i+1,pixKeys.get(i).getKeyValue());
                                    }
                                }
                                System.out.println("Informe a chave pix para cadastrar:");
                                keyPix = sc.next();
                                pixKeyController.insert(keyPix, actualAccount);
                                System.out.println("Chave cadastrada com sucesso!");
                                break;
                        }
                        break;
                    case 4:
                        System.out.println("Informe o valor que será adicionado na conta:");
                        money = sc.nextBigDecimal();
                        try {
                            accountController.deposit(actualAccount.getId(), money);
                            System.out.println("Valor adicionado!");
                        } catch (NegativeValueException e) {
                            System.out.println("Erro " + e.getMessage());
                        }

                        break;
                    case 5:
                        System.out.println("Saindo do sistema");
                        break;
                    default:
                        System.out.println("Erro: Valor digitado inválido");
                        break;
                }

            }
        }
        } while (selection != 3);
            sc.close();
            System.out.println("Obrigado por usar o LV Bank!");

    }

}