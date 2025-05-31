package br.com.dalq.fiap_tech_challenge.services;

import br.com.dalq.fiap_tech_challenge.dtos.UsuarioRequestDTO;
import br.com.dalq.fiap_tech_challenge.entities.Usuario;
import br.com.dalq.fiap_tech_challenge.repositories.UsuarioRepository;
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

    public List<Usuario> findAllUsuarios(int page, int size) {
        int offset = (page - 1) * size;

        return this.usuarioRepository.findAll(size, offset);
    }

    public Optional<Usuario> findUsuarioById(long id) {
        return this.usuarioRepository.findById(id);
    }

    public void saveUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        var usuario = new Usuario(usuarioRequestDTO);
        var save = this.usuarioRepository.save(usuario);
        Assert.state(save == 1, "Erro ao salvar usuario " + usuario.getNome());
    }

    public void updateUsuario(UsuarioRequestDTO usuarioRequestDTO, Long id) {
        var usuario = new Usuario(usuarioRequestDTO);
        var update = this.usuarioRepository.update(usuario, id);
        if (update == 0) {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public void deleteUsuario(Long id) {
        var delete = this.usuarioRepository.delete(id);
        if (delete == 0) {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}
