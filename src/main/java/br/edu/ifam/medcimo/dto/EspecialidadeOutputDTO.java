package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Especialidade;

public class EspecialidadeOutputDTO {
    private long id;
    private String nome;
    private String descricao;

    public EspecialidadeOutputDTO() {
    }

    public EspecialidadeOutputDTO(Especialidade especialidade) {
        this.id = especialidade.getId();
        this.nome = especialidade.getNome();
        this.descricao = especialidade.getDescricao();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
