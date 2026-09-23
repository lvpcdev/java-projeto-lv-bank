package br.com.lucasvicente.contabancaria.repository;

import br.com.lucasvicente.contabancaria.entites.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByCpf(String cpf);
}
