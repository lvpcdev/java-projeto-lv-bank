package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.entites.Person;
import br.com.lucasvicente.contabancaria.service.PersonService;

import java.util.List;

public class PersonController {

    private final PersonService service = new PersonService();

    public List<Person> findAll() {
        return service.findAll();
    }

    public Person findById(long id) {
        return service.findById(id);
    }

    public void insert (String fullName, String cpf) {
        Person person = new Person();
        person.setFullName(fullName);
        person.setCpf(cpf);
        service.insert(person);
    }

    public void update (long id, String fullName, String cpf) {
        Person person = new Person();
        person.setFullName(fullName);
        person.setCpf(cpf);
        service.update(person);
    }

    public void delete(long id) {
        service.delete(id);
    }
}
