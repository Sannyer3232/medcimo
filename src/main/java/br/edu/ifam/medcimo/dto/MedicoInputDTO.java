package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Medico;
import br.edu.ifam.medcimo.repository.EspecialidadeRepository;

public class MedicoInputDTO extends FuncionarioInputDTO{

    private String crm;
    private long especialidade;

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public long getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(long especialidade) {
        this.especialidade = especialidade;
    }

    public Medico build(EspecialidadeRepository especialidadeRepository){
        Medico medico = new Medico();
        medico.setNome(this.getNome());
        medico.setCargo(this.getCargo());
        medico.setDepartamento(this.getDepartamento());
        medico.setEmail(this.getEmail());
        medico.setTelefone(this.getTelefone());
        medico.setEndereco(this.getEndereco().build());
        medico.setCrm(this.getCrm());
        medico.setEspecialidade(especialidadeRepository.findById(this.getEspecialidade()).orElse(null));
        return medico;
    }
}
