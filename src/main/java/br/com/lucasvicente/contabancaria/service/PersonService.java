package br.com.lucasvicente.contabancaria.service;
import br.com.lucasvicente.contabancaria.dto.ChangePasswordDTO.ChangePasswordRequestDTO;
import br.com.lucasvicente.contabancaria.dto.LoginDTO.LoginRequestDTO;
import br.com.lucasvicente.contabancaria.repository.PersonRepository;
import br.com.lucasvicente.contabancaria.dto.PersonDTO.PersonRequestDTO;
import br.com.lucasvicente.contabancaria.dto.PersonDTO.PersonResponseDTO;
import br.com.lucasvicente.contabancaria.entites.Person;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<PersonResponseDTO> findAll() {
        return personRepository.findAll().stream().map(this::toDTO).toList();
    }

    public PersonResponseDTO findById(Long id) {
        Person existsPerson = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        return toDTO(existsPerson);
    }

    public PersonResponseDTO insert(PersonRequestDTO dto) {
        Person existingPerson = personRepository.findByCpf(dto.cpf());
        if (existingPerson != null) {
            if (!passwordEncoder.matches(dto.password(), existingPerson.getPassword())) {
                throw new IllegalArgumentException("Senha incorreta para o CPF informado");
            }
            return toDTO(existingPerson);
        }
        Person person = new Person();
        person.setFullName(dto.fullName());
        person.setCpf(dto.cpf());
        person.setPassword(passwordEncoder.encode(dto.password()));

        return toDTO(personRepository.save(person));
    }

    public void delete(Long id) {

        Person existsPerson = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        personRepository.deleteById(existsPerson.getId());
    }

    public PersonResponseDTO changePassword(Long id, ChangePasswordRequestDTO dto) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        existingPerson.setPassword(passwordEncoder.encode(dto.newPassword()));

        return toDTO(personRepository.save(existingPerson));
    }

    public PersonResponseDTO login(LoginRequestDTO dto) {
        Person existingPerson = personRepository.findByCpf(dto.cpf());

        if (existingPerson == null) throw new IllegalArgumentException("Pessoa não encontrada.");

        if (passwordEncoder.matches(dto.password(), existingPerson.getPassword())) {
            return toDTO(existingPerson);
        } else {
            throw new IllegalArgumentException("Senha inválida");
        }
    }

    public PersonResponseDTO toDTO(Person person) {
        return new PersonResponseDTO(
                person.getId(),
                person.getFullName(),
                person.getCpf()
        );
    }

}
