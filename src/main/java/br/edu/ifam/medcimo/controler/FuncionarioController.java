package br.edu.ifam.medcimo.controler;

import br.edu.ifam.medcimo.dto.FuncionarioInputDTO;
import br.edu.ifam.medcimo.dto.FuncionarioOutputDTO;
import br.edu.ifam.medcimo.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@Tag(name = "Funcionários", description = "APIs para gerenciamento de funcionários")
@CrossOrigin(origins = "*")
public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar todos os funcionários", description = "Retorna uma lista de todos os funcionários registrados")
    public ResponseEntity<List<FuncionarioOutputDTO>> list() {
        try {
            List<FuncionarioOutputDTO> funcionarios = funcionarioService.listaFuncionario();
            if (!funcionarios.isEmpty()) {
                return new ResponseEntity<>(funcionarios, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar funcionário por ID", description = "Retorna os detalhes de um funcionário específico")
    public ResponseEntity<FuncionarioOutputDTO> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(funcionarioService.findFuncionarioById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Inserir funcionário", description = "Insere os dados de um funcionário e retorna o objeto salvo")
    public ResponseEntity<EntityModel<FuncionarioOutputDTO>> create(@RequestBody FuncionarioInputDTO funcionarioInputDTO, UriComponentsBuilder uriBuilder) {
        try {
            FuncionarioOutputDTO funcionarioSalvo = funcionarioService.creat(funcionarioInputDTO);

            UriComponents uriComponents = uriBuilder.path("/api/funcionarios/{id}")
                    .buildAndExpand(funcionarioSalvo.getId());

            URI uri = uriComponents.toUri();

            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(FuncionarioController.class).getById(funcionarioSalvo.getId())
            ).withSelfRel();

            Link deleteLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(FuncionarioController.class).delete(funcionarioSalvo.getId())
            ).withRel("delete-funcionario");

            EntityModel<FuncionarioOutputDTO> resource = EntityModel.of(funcionarioSalvo, selfLink, deleteLink);

            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar dados de funcionário", description = "Atualiza os dados de um funcionário e retorna o objeto atualizado")
    public ResponseEntity<FuncionarioOutputDTO> update(@PathVariable Long id, @RequestBody FuncionarioInputDTO funcionarioInputDTO) {
        try {
            return new ResponseEntity<>(funcionarioService.update(id, funcionarioInputDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletar funcionário por ID", description = "Deleta um funcionário específico")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            funcionarioService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
