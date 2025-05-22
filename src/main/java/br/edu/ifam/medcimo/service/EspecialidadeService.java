package br.edu.ifam.medcimo.service;

import br.edu.ifam.medcimo.dto.EspecialidadeInputDTO;
import br.edu.ifam.medcimo.dto.EspecialidadeOutputDTO;
import br.edu.ifam.medcimo.model.Especialidade;
import br.edu.ifam.medcimo.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadeService {
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public List<EspecialidadeOutputDTO> listarEspecialidades() {
        try {
            List<EspecialidadeOutputDTO> listaDTO = new ArrayList<>();
            especialidadeRepository.findAll().forEach(especialidade ->
                    listaDTO.add(new EspecialidadeOutputDTO(especialidade))
            );
            return listaDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EspecialidadeOutputDTO buscarPorId(Long id) {
        try {
            return new EspecialidadeOutputDTO(especialidadeRepository.findById(id).get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EspecialidadeOutputDTO create(EspecialidadeInputDTO dto) {
        try {
            return new EspecialidadeOutputDTO(especialidadeRepository.save(dto.build()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EspecialidadeOutputDTO atualizar(Long id, EspecialidadeInputDTO dto) {
        try {
            Especialidade especialidadeExistente = especialidadeRepository.findById(id).get();
            Especialidade especialidadeUpdate = dto.build();
            especialidadeExistente.setNome(especialidadeUpdate.getNome());
            especialidadeExistente.setDescricao(especialidadeUpdate.getDescricao());

            Especialidade atualizada = especialidadeRepository.save(especialidadeExistente);
            return new EspecialidadeOutputDTO(atualizada);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deletar(Long id) {
        try {
            Especialidade especialidade = especialidadeRepository.findById(id).get();
            especialidadeRepository.delete(especialidade);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
