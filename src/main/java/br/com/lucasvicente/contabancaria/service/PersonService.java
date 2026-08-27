package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.PersonDao;
import br.com.lucasvicente.contabancaria.dto.requests.PersonRequestDTO;
import br.com.lucasvicente.contabancaria.dto.responses.PersonResponseDTO;
import br.com.lucasvicente.contabancaria.entites.Person;

import java.util.List;

public class PersonService {
    private final PersonDao personDao = new PersonDao();

    public List<PersonResponseDTO> findAll() {
        return personDao.findAll().stream().map(this::toDTO).toList();
    }

    public PersonResponseDTO findById(Long id) {
        return toDTO(personDao.findById(id));
    }

    public PersonResponseDTO insert(PersonRequestDTO dto) {

        Person person = new Person();

        person.setFullName(dto.fullName());
        person.setCpf(dto.cpf());

        return toDTO(personDao.insert(person));
    }

    public void delete(Long id) {
        personDao.deleteById(id);
    }

    public PersonResponseDTO update(Long id, PersonRequestDTO dto) {
        Person existingPerson = personDao.findById(id);

        if (existingPerson == null) {
            throw new IllegalArgumentException("pessoa não encontrada");
        }

        existingPerson.setCpf(dto.cpf());
        existingPerson.setFullName(dto.fullName());

        return toDTO(personDao.update(existingPerson));
    }

    public PersonResponseDTO toDTO(Person person) {
        return new PersonResponseDTO(
                person.getId(),
                person.getFullName(),
                person.getCpf()
        );
    }

}
