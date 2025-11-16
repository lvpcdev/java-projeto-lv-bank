package br.com.lucasvicente.contabancaria.entites;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String fullName;
    private String cpf;
    private String password;
    private List<String> keyPix;


    public Person(String fullName, String cpf, String password) {
        this.fullName = fullName;
        this.cpf = cpf;
        this.password = password;
        this.keyPix = new ArrayList<>();
    }

    public Person(){

    }

    // Getters
    public String getFullName() {
        return fullName; }
    public String getCpf() {
        return cpf; }
    public String getPassword() {
        return password; }
    public List<String> getKeyPix() {
        return keyPix;
    }

    // Setters
    public void setFullName(String fullName) {
        this.fullName = fullName; }
    public void setCpf(String cpf) {
        this.cpf = cpf; }
    public void setPassword(String password) {
        this.password = password; }



    public void addKeyPix(String key) {
        keyPix.add(key);
    }
}