package br.com.dalq.fiap_tech_challenge.services;

import br.com.dalq.fiap_tech_challenge.dtos.EnderecoRequestDTO;
import br.com.dalq.fiap_tech_challenge.entities.Endereco;
import br.com.dalq.fiap_tech_challenge.repositories.EnderecoRepository;
import br.com.dalq.fiap_tech_challenge.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<Endereco> findByUserId(Long usuarioId) {
        return this.enderecoRepository.findByUserId(usuarioId);
    }

    public Optional<Endereco> findById(long id) {
        return this.enderecoRepository.findById(id);
    }

    public void save(EnderecoRequestDTO enderecoRequestDTO) {
        var endereco = new Endereco(enderecoRequestDTO);
        var save = this.enderecoRepository.save(endereco);
        Assert.state(save == 1, "Erro ao salvar endereço");
    }

    public void update(EnderecoRequestDTO enderecoRequestDTO, Long id) {
        var endereco = new Endereco(enderecoRequestDTO);
        var update = this.enderecoRepository.update(endereco, id);
        if (update == 0) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
    }

    public void delete(Long id) {
        var delete = this.enderecoRepository.delete(id);
        if (delete == 0) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
    }
}
