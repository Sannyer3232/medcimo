package br.edu.ifam.medcimo.service;

import br.edu.ifam.medcimo.dto.MedicoInputDTO;
import br.edu.ifam.medcimo.dto.MedicoOutputDTO;
import br.edu.ifam.medcimo.model.Medico;
import br.edu.ifam.medcimo.repository.EspecialidadeRepository;
import br.edu.ifam.medcimo.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public List<MedicoOutputDTO> listarMedicos() {
        try {
            List<Medico> medicos = medicoRepository.findAll();
            List<MedicoOutputDTO> medicoOutputDTOS = new ArrayList<>();
            for (Medico medico : medicos) {
                medicoOutputDTOS.add(new MedicoOutputDTO(medico));
            }
            return medicoOutputDTOS;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MedicoOutputDTO buscarPorId(Long id) {
        try {
            Medico medico = medicoRepository.findById(id).get();
            return new MedicoOutputDTO(medico);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MedicoOutputDTO buscarPorCrm(String crm) {
        try {
            Medico medico = medicoRepository.findMedicoByCrm(crm);
            if (medico == null) {
                throw new RuntimeException("Médico não encontrado com CRM: " + crm);
            }
            return new MedicoOutputDTO(medico);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MedicoOutputDTO create(MedicoInputDTO medicoInputDTO) {
        try {
            Medico medico = medicoInputDTO.build(especialidadeRepository);
            medico = medicoRepository.save(medico);
            return new MedicoOutputDTO(medico);
        } catch (Exception e) {
            throw new RuntimeException( e);
        }
    }

    public MedicoOutputDTO upadte(Long id, MedicoInputDTO medicoInputDTO) {
        try {
            Medico medicoExistente = medicoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
            Medico medicoAtualizado = medicoInputDTO.build(especialidadeRepository);

            // Atualiza os dados do médico existente
            medicoExistente.setNome(medicoAtualizado.getNome());
            medicoExistente.setEmail(medicoAtualizado.getEmail());
            medicoExistente.setEndereco(medicoAtualizado.getEndereco());
            medicoExistente.setTelefone(medicoAtualizado.getTelefone());
            medicoExistente.setCargo(medicoAtualizado.getCargo());
            medicoExistente.setDepartamento(medicoAtualizado.getDepartamento());
            medicoExistente.setCrm(medicoAtualizado.getCrm());
            medicoExistente.setEspecialidade(medicoAtualizado.getEspecialidade());

            medicoExistente = medicoRepository.save(medicoExistente);
            return new MedicoOutputDTO(medicoExistente);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deletar(Long id) {
        try {
            Medico medico = medicoRepository.findById(id).get();
            medicoRepository.delete(medico);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
