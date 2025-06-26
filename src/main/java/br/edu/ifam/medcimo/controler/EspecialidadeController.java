package br.edu.ifam.medcimo.controler;

import br.edu.ifam.medcimo.dto.EspecialidadeInputDTO;
import br.edu.ifam.medcimo.dto.EspecialidadeOutputDTO;
import br.edu.ifam.medcimo.service.EspecialidadeService;
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
@RequestMapping("/api/especialidades")
@Tag(name = "Especialidades", description = "APIs para gerenciamento de especialidades médicas")
@CrossOrigin(origins = "*")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar especialidades", description = "Retorna uma lista de todas as especialidades")
    public ResponseEntity<List<EspecialidadeOutputDTO>> list() {
        try {
            List<EspecialidadeOutputDTO> lista = especialidadeService.listarEspecialidades();
            if (!lista.isEmpty()) {
                return new ResponseEntity<>(lista, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar especialidade por ID", description = "Retorna os dados de uma especialidade específica")
    public ResponseEntity<EspecialidadeOutputDTO> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(especialidadeService.buscarPorId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Inserir especialidade", description = "Insere uma nova especialidade no banco de dados")
    public ResponseEntity<EntityModel<EspecialidadeOutputDTO>> create(@RequestBody EspecialidadeInputDTO dto,
                                                                      UriComponentsBuilder uriComponentsBuilder) {
        try {
            EspecialidadeOutputDTO especialidadeSalva = especialidadeService.create(dto);

            UriComponents uriComponents = uriComponentsBuilder
                    .path("/api/especialidades/{id}")
                    .buildAndExpand(especialidadeSalva.getId());

            URI uri = uriComponents.toUri();

            Link selfLink = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(EspecialidadeController.class)
                                    .getById(especialidadeSalva.getId()))
                    .withSelfRel();

            Link deleteLink = WebMvcLinkBuilder.linkTo(
                            WebMvcLinkBuilder.methodOn(EspecialidadeController.class)
                                    .delete(especialidadeSalva.getId()))
                    .withRel("delete-especialidade");

            EntityModel<EspecialidadeOutputDTO> resource = EntityModel.of(especialidadeSalva, selfLink, deleteLink);

            return ResponseEntity.created(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resource);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar especialidade", description = "Atualiza os dados de uma especialidade específica")
    public ResponseEntity<EspecialidadeOutputDTO> update(@PathVariable Long id, @RequestBody EspecialidadeInputDTO dto) {
        try {
            return new ResponseEntity<>(especialidadeService.atualizar(id, dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletar especialidade", description = "Remove uma especialidade pelo ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            especialidadeService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
