package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.dto.requests.PersonRequestDTO;
import br.com.lucasvicente.contabancaria.dto.responses.PersonResponseDTO;
import br.com.lucasvicente.contabancaria.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController{

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    public List<PersonResponseDTO> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public PersonResponseDTO findById(@PathVariable Long id){
        return findById(id);
    }

    @PostMapping
    public PersonResponseDTO insert (@Valid @RequestBody PersonRequestDTO dto) {
        return personService.insert(dto);
    }

    @PutMapping("/{id}")
    public PersonResponseDTO update (@PathVariable Long id, @Valid @RequestBody PersonRequestDTO dto) {
        return personService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)  {
        personService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
