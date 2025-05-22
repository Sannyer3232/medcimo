package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Permissao;

public class PermissaOutputDTO {

    private long id;
    private String nivelAcesso;
    private String descricao;
    private String funcionario;

    public PermissaOutputDTO() {
    }

    public PermissaOutputDTO(Permissao permissao) {
        this.id = permissao.getId();
        this.nivelAcesso = permissao.getNivelAcesso();
        this.descricao = permissao.getDescricao();
        this.funcionario = permissao.getFuncionario().getNome();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }
}
