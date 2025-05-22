package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Funcionario;
import br.edu.ifam.medcimo.model.Medico;

public class MedicoOutputDTO extends FuncionarioOutputDTO{

    private String crm;
    private String especialidade;

    public MedicoOutputDTO() {
        super();
    }

    public MedicoOutputDTO(Medico medico) {
        super(medico);
        this.crm = medico.getCrm();
        this.especialidade = medico.getEspecialidade().getNome();
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
