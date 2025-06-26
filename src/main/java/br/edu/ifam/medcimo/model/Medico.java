package br.edu.ifam.medcimo.model;

import jakarta.persistence.*;

@Entity
public class Medico extends Funcionario{

    @Column(unique=true, nullable=false)
    private String crm;

    @ManyToOne
    @JoinColumn(name = "especialidade_id")
    private Especialidade especialidade;

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
