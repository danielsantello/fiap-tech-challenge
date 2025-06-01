package br.com.dalq.fiap_tech_challenge.controllers;

import br.com.dalq.fiap_tech_challenge.context.OnCreate;
import br.com.dalq.fiap_tech_challenge.context.OnUpdate;
import br.com.dalq.fiap_tech_challenge.dtos.EnderecoRequestDTO;
import br.com.dalq.fiap_tech_challenge.dtos.UsuarioRequestDTO;
import br.com.dalq.fiap_tech_challenge.entities.Endereco;
import br.com.dalq.fiap_tech_challenge.entities.Usuario;
import br.com.dalq.fiap_tech_challenge.services.EnderecoService;
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
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Gerencia os endereços dos usuários")
public class EnderecoController {
    private static final Logger logger = LoggerFactory.getLogger(EnderecoController.class);

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    // http://localhost:8080/enderecos/usuarios/1
    @GetMapping("/usuarios/{id}")
    @Operation(
            description = "Retorna um conjunto de endereços do usuário do id informado",
            summary = "Busca todos os endereços do usuário do id informado",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<List<Endereco>> findByUserId(
            @PathVariable("id") Long usuarioId
    ) {
        logger.info("GET /enderecos");
        var enderecos = this.enderecoService.findByUserId(usuarioId);
        return ResponseEntity.ok(enderecos);
    }


    // http://localhost:8080/enderecos/1
    @GetMapping("/{id}")
    @Operation(
            description = "Retorna os dados do endereço do id informado",
            summary = "Busca um endereço",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<Optional<Endereco>> findById(
            @PathVariable("id") Long id
    ) {
        logger.info("GET /enderecos/" + id);
        var endereco = this.enderecoService.findById(id);
        return ResponseEntity.ok(endereco);
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
    public ResponseEntity<Void> save(
            @Validated(OnCreate.class)  @RequestBody EnderecoRequestDTO enderecoRequestDTO
    ) {
        logger.info("POST /enderecos");
        this.enderecoService.save(enderecoRequestDTO);
        return ResponseEntity.status(201).build();
    }


    @PutMapping("/{id}")
    @Operation(
            description = "Altera os dados do endereço do id informado",
            summary = "Altera um endereço",
            responses = {
                    @ApiResponse(
                            responseCode = "201"
                    )
            }
    )
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @Validated(OnUpdate.class)  @RequestBody EnderecoRequestDTO enderecoRequestDTO
    ) {
        logger.info("PUT /enderecos/" + id);
        this.enderecoService.update(enderecoRequestDTO, id);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{id}")
    @Operation(
            description = "Exclui o endereço do id informado",
            summary = "Exclui um endereço",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id
    ) {
        logger.info("DELETE /enderecos/" + id);
        this.enderecoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
