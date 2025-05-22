package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Especialidade;

public class EspecialidadeInputDTO {
    private String nome;
    private String descricao;

    public EspecialidadeInputDTO(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public EspecialidadeInputDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Especialidade build(){
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(this.nome);
        especialidade.setDescricao(this.descricao);
        return especialidade;
    }
}
