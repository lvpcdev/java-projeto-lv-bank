package br.com.lucasvicente.contabancaria.service;

import br.com.lucasvicente.contabancaria.dao.PixKeyDao;
import br.com.lucasvicente.contabancaria.entites.PixKey;

import java.util.List;

public class PixKeyService {
    private final PixKeyDao dao = new PixKeyDao();

    public List<PixKey> findAll() {
        return dao.findAll();
    }

    public PixKey findById(long id) {
        return dao.findById(id);
    }

    public PixKey insert(PixKey pixKey) {
        return dao.insert(pixKey);
    }

    public void delete(long id) {
        dao.deleteById(id);
    }

    public void update(PixKey pixKey) {
        dao.update(pixKey);
    }

    public List<PixKey> findAllByAccountId(Long id) {
        return dao.findAllByAccountId(id);
    }
}
