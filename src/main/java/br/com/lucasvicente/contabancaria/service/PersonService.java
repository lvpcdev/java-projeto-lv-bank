package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.PersonDao;
import br.com.lucasvicente.contabancaria.entites.Person;

import java.util.List;

public class PersonService {
    private final PersonDao dao = new PersonDao();

    public List<Person> findAll() {
        return dao.findAll();
    }

    public Person findById(long id) {
        return dao.findById(id);
    }

    public Person insert(Person person) {
        return dao.insert(person);
    }

    public void delete(long id) {
        dao.deleteById(id);
    }

    public void update(Person person) {
        dao.update(person);
    }

}
