package br.com.dalq.fiap_tech_challenge.services;

import br.com.dalq.fiap_tech_challenge.dtos.UsuarioRequestDTO;
import br.com.dalq.fiap_tech_challenge.entities.Usuario;
import br.com.dalq.fiap_tech_challenge.repositories.UsuarioRepository;
import br.com.dalq.fiap_tech_challenge.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll(int page, int size) {
        int offset = (page - 1) * size;

        return this.usuarioRepository.findAll(size, offset);
    }

    public Optional<Usuario> findById(long id){
        return this.usuarioRepository.findById(id);
    }

    public void save(UsuarioRequestDTO usuarioRequestDTO) {
        var usuario = new Usuario(usuarioRequestDTO);
        var save = this.usuarioRepository.save(usuario);
        Assert.state(save == 1, "Erro ao salvar usuario " + usuario.getNome());
    }

    public void update(UsuarioRequestDTO usuarioRequestDTO, Long id) {
        var usuario = new Usuario(usuarioRequestDTO);
        var update = this.usuarioRepository.update(usuario, id);
        if (update == 0) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }

    public void delete(Long id) {
        var delete = this.usuarioRepository.delete(id);
        if (delete == 0) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
    }
}
