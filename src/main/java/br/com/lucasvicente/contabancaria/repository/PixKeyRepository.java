package br.com.lucasvicente.contabancaria.repository;

import br.com.lucasvicente.contabancaria.entites.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey,Long> {
    List<PixKey> findAllByAccountId(Long accountId);
}
