package br.edu.ifam.medcimo.controler;

import br.edu.ifam.medcimo.dto.MedicoInputDTO;
import br.edu.ifam.medcimo.dto.MedicoOutputDTO;
import br.edu.ifam.medcimo.service.MedicoService;
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
@RequestMapping("/api/medico")
@Tag(name = "Médicos", description = "APIs para gerenciamento de médicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar todos os médicos", description = "Retorna uma lista com todos os médicos cadastrados")
    public ResponseEntity<List<MedicoOutputDTO>> listar() {
        try {
            List<MedicoOutputDTO> medicos = medicoService.listarMedicos();
            if (!medicos.isEmpty()) {
                return new ResponseEntity<>(medicos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar médico por ID", description = "Retorna os detalhes de um médico pelo ID")
    public ResponseEntity<MedicoOutputDTO> buscarPorId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(medicoService.buscarPorId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/crm/{crm}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar médico por CRM", description = "Retorna os detalhes de um médico pelo CRM")
    public ResponseEntity<MedicoOutputDTO> buscarPorCrm(@PathVariable String crm) {
        try {
            return new ResponseEntity<>(medicoService.buscarPorCrm(crm), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo médico", description = "Insere os dados de um novo médico e retorna o objeto salvo")
    public ResponseEntity<EntityModel<MedicoOutputDTO>> criar(@RequestBody MedicoInputDTO medicoInputDTO, UriComponentsBuilder uriBuilder) {
        try {
            MedicoOutputDTO medicoSalvo = medicoService.create(medicoInputDTO);

            UriComponents uriComponents = uriBuilder.path("/api/medico/{id}").buildAndExpand(medicoSalvo.getId());
            URI uri = uriComponents.toUri();

            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(MedicoController.class).buscarPorId(medicoSalvo.getId())
            ).withSelfRel();

            Link deleteLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(MedicoController.class).deletar(medicoSalvo.getId())
            ).withRel("delete-medico");

            EntityModel<MedicoOutputDTO> resource = EntityModel.of(medicoSalvo, selfLink, deleteLink);

            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar médico", description = "Atualiza os dados de um médico existente")
    public ResponseEntity<MedicoOutputDTO> atualizar(@PathVariable Long id, @RequestBody MedicoInputDTO medicoInputDTO) {
        try {
            MedicoOutputDTO medicoAtualizado = medicoService.upadte(id, medicoInputDTO);
            return new ResponseEntity<>(medicoAtualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletar médico", description = "Remove um médico pelo ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            medicoService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
