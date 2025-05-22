package br.edu.ifam.medcimo.controler;

import br.edu.ifam.medcimo.dto.EscalaInputDTO;
import br.edu.ifam.medcimo.dto.EscalaOutputDTO;
import br.edu.ifam.medcimo.service.EscalaService;
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
@RequestMapping("/api/escala")
@Tag(name = "Escala", description = "API para gerenciamento de escalas")
public class EscalaController {

    @Autowired
    private EscalaService escalaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar todas as escalas", description = "Retorna uma lista de todas as escalas cadastradas")
    public ResponseEntity<List<EscalaOutputDTO>> list() {
        try {
            List<EscalaOutputDTO> escalas = escalaService.list();
            if (!escalas.isEmpty()) {
                return new ResponseEntity<>(escalas, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar escala por ID", description = "Retorna os detalhes de uma escala específica")
    public ResponseEntity<EscalaOutputDTO> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(escalaService.getById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar nova escala", description = "Cria uma nova escala e retorna o objeto salvo com links")
    public ResponseEntity<EntityModel<EscalaOutputDTO>> create(
            @RequestBody EscalaInputDTO escalaInputDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        try {
            EscalaOutputDTO escalaSalva = escalaService.creat(escalaInputDTO);

            UriComponents uriComponents = uriComponentsBuilder
                    .path("/api/escala/{id}")
                    .buildAndExpand(escalaSalva.getId());

            URI uri = uriComponents.toUri();

            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(EscalaController.class).getById(escalaSalva.getId())
            ).withSelfRel();

            Link deleteLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(EscalaController.class).delete(escalaSalva.getId())
            ).withRel("delete-escala");

            EntityModel<EscalaOutputDTO> resource = EntityModel.of(escalaSalva, selfLink, deleteLink);

            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar escala", description = "Atualiza uma escala existente e retorna o objeto atualizado")
    public ResponseEntity<EscalaOutputDTO> update(@PathVariable Long id, @RequestBody EscalaInputDTO escalaInputDTO) {
        try {
            EscalaOutputDTO updated = escalaService.update(id, escalaInputDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletar escala", description = "Exclui uma escala com base no ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            escalaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
