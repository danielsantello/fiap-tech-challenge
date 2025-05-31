package br.com.dalq.fiap_tech_challenge.controllers;

import br.com.dalq.fiap_tech_challenge.dtos.UsuarioRequestDTO;
import br.com.dalq.fiap_tech_challenge.entities.Usuario;
import br.com.dalq.fiap_tech_challenge.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // http://localhost:8080/usuarios?page=1 (query params)
    // http://localhost:8080/usuarios?page=1&size=10 (query params)
    @GetMapping
    @Operation(
            description = "Busca todos os veículos",
            summary = "Busca Todos",
            responses = {
                    @ApiResponse(
                            description = "Operation Responses Message",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<List<Usuario>> findAllUsuarios(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        logger.info("GET /usuarios");
        var usuarios = this.usuarioService.findAllUsuarios(page, size);
        return ResponseEntity.ok(usuarios);
    }

    // http://localhost:8080/usuarios/1 (path params)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> findUsuario(
            @PathVariable("id") Long id
    ) {
        logger.info("GET /usuarios/" + id);
        var usuario = this.usuarioService.findUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Void> saveUsuario(
            @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {
        logger.info("POST /usuarios");
        this.usuarioService.saveUsuario(usuarioRequestDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUsuario(
            @PathVariable("id") Long id,
            @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {
        logger.info("PUT /usuarios/" + id);
        this.usuarioService.updateUsuario(usuarioRequestDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable("id") Long id
    ) {
        logger.info("DELETE /usuarios/" + id);
        this.usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }
}
