package br.com.dalq.fiap_tech_challenge.controllers;

import br.com.dalq.fiap_tech_challenge.context.OnCreate;
import br.com.dalq.fiap_tech_challenge.context.OnUpdate;
import br.com.dalq.fiap_tech_challenge.dtos.UsuarioRequestDTO;
import br.com.dalq.fiap_tech_challenge.dtos.UsuarioSetPasswordDTO;
import br.com.dalq.fiap_tech_challenge.dtos.ValidationLoginDTO;
import br.com.dalq.fiap_tech_challenge.entities.Usuario;
import br.com.dalq.fiap_tech_challenge.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Gerencia os usuários do sistema")
public class UsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // http://localhost:8080/usuarios
    // http://localhost:8080/usuarios?page=1
    // http://localhost:8080/usuarios?page=1&size=10
    @GetMapping
    @Operation(
            description = "Retorna um conjunto de usuários. É possível definir a página e o tamanho dela, através das querys strings page e size respectivamente.",
            summary = "Busca todos os usuários",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<List<Usuario>> findAllUsuarios(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        logger.info("GET /usuarios");
        var usuarios = this.usuarioService.findAll(page, size);
        return ResponseEntity.ok(usuarios);
    }

    // http://localhost:8080/usuarios/1 (path params)
    @GetMapping("/{id}")
    @Operation(
            description = "Retorna os dados do usuário do id informado",
            summary = "Busca um usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<Optional<Usuario>> findUsuario(
            @PathVariable("id") Long id
    ) {
        logger.info("GET /usuarios/" + id);
        var usuario = this.usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Operation(
            description = "Cria um novo usuário",
            summary = "Cria um novo usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "201"
                    )
            }
    )
    public ResponseEntity<Void> saveUsuario(
            @Validated(OnCreate.class) @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {
        logger.info("POST /usuarios");
        this.usuarioService.save(usuarioRequestDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    @Operation(
            description = "Altera os dados do usuário do id informado",
            summary = "Altera um usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "201"
                    )
            }
    )
    public ResponseEntity<Void> updateUsuario(
            @PathVariable("id") Long id,
            @Validated(OnUpdate.class) @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {
        logger.info("PUT /usuarios/" + id);
        this.usuarioService.update(usuarioRequestDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Exclui o usuário do id informado",
            summary = "Exclui um usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable("id") Long id
    ) {
        logger.info("DELETE /usuarios/" + id);
        this.usuarioService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(
            description = "Valida se existe o usuário e senha cadastrados no banco",
            summary = "Validar login e senha",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<Void> validateLogin(
            @Valid @RequestBody ValidationLoginDTO validationLoginDTO
    ) {
        logger.info("POST /validateLogin");
        this.usuarioService.validateLogin(validationLoginDTO);
        return ResponseEntity.status(200).build();
    }

    @PutMapping("/password/{id}")
    @Operation(
            description = "Altera a senha do usuário do id informado",
            summary = "Altera uma senha",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<Void> setPassword(
            @PathVariable("id") Long id,
            @Valid @RequestBody UsuarioSetPasswordDTO usuarioSetPasswordDTO
            ) {
        logger.info("PUT /usuarios/password" + id);
        this.usuarioService.setPassword(usuarioSetPasswordDTO, id);
        return ResponseEntity.ok().build();
    }
}
