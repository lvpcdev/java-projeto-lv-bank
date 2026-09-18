package br.com.lucasvicente.contabancaria.controller;

import br.com.lucasvicente.contabancaria.dto.PixKeyDTO.PixKeyRequestDTO;
import br.com.lucasvicente.contabancaria.dto.PixKeyDTO.PixKeyResponseDTO;
import br.com.lucasvicente.contabancaria.service.PixKeyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pixkeys")
public class PixKeyController{

    private final PixKeyService pixKeyService;

    public PixKeyController(PixKeyService pixKeyService) {
        this.pixKeyService = pixKeyService;
    }

    @GetMapping
    public List<PixKeyResponseDTO> findAll(){
        return pixKeyService.findAll();
    }

    @GetMapping("/{id}")
    public PixKeyResponseDTO findById(@PathVariable Long id) {
        return pixKeyService.findById(id);
    }

    @PostMapping
    public PixKeyResponseDTO insert (@Valid @RequestBody PixKeyRequestDTO dto) {
        return pixKeyService.insert(dto);
    }

    @PutMapping("/{id}")
    public PixKeyResponseDTO update (@PathVariable Long id, @Valid @RequestBody PixKeyRequestDTO dto) {
        return pixKeyService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pixKeyService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/account/{accountId}")
    public List<PixKeyResponseDTO> findAllByAccountId(@PathVariable Long accountId){
        return pixKeyService.findAllByAccountId(accountId);
    }
}
