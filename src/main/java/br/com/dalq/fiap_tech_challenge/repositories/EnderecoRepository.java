package br.com.dalq.fiap_tech_challenge.repositories;

import br.com.dalq.fiap_tech_challenge.entities.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository {
    List<Endereco> findByUserId(long usuarioId);

    Optional<Endereco> findById(long id);

    Integer save(Endereco endereco);

    Integer update(Endereco endereco, Long id);

    Integer delete(Long id);
}
