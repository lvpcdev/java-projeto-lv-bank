package br.com.lucasvicente.contabancaria.service;
import br.com.lucasvicente.contabancaria.repository.PersonRepository;
import br.com.lucasvicente.contabancaria.dto.PersonDTO.PersonRequestDTO;
import br.com.lucasvicente.contabancaria.dto.PersonDTO.PersonResponseDTO;
import br.com.lucasvicente.contabancaria.entites.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonResponseDTO> findAll() {
        return personRepository.findAll().stream().map(this::toDTO).toList();
    }

    public PersonResponseDTO findById(Long id) {
        Person existsPerson = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        return toDTO(existsPerson);
    }

    public PersonResponseDTO insert(PersonRequestDTO dto) {

        Person person = new Person();

        person.setFullName(dto.fullName());
        person.setCpf(dto.cpf());

        return toDTO(personRepository.save(person));
    }

    public void delete(Long id) {

        Person existsPerson = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        personRepository.deleteById(existsPerson.getId());
    }

    public PersonResponseDTO update(Long id, PersonRequestDTO dto) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        existingPerson.setCpf(dto.cpf());
        existingPerson.setFullName(dto.fullName());

        return toDTO(personRepository.save(existingPerson));
    }

    public PersonResponseDTO toDTO(Person person) {
        return new PersonResponseDTO(
                person.getId(),
                person.getFullName(),
                person.getCpf()
        );
    }

}
