package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Medico;
import br.edu.ifam.medcimo.repository.EspecialidadeRepository;

public class MedicoInputDTO extends FuncionarioInputDTO{

    private String crm;
    private long especialidade;

    public Medico build(EspecialidadeRepository especialidadeRepository){
        Medico medico = new Medico();
        medico.setCrm(crm);
        medico.setEspecialidade(especialidadeRepository.findById(especialidade).get());
        return medico;
    }
}
