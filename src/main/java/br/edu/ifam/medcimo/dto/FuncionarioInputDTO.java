package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Funcionario;

public class FuncionarioInputDTO {


    private String nome;
    private String cargo;
    private String departamento;
    private String email;
    private String telefone;
    private EnderecoInputDTO endereco;
    private String senha;
    private String permissao;

    public FuncionarioInputDTO() {}

    public FuncionarioInputDTO(String nome, String cargo, String departamento, String email, String telefone, EnderecoInputDTO endereco, String senha, String permissao) {
        this.nome = nome;
        this.cargo = cargo;
        this.departamento = departamento;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.senha = senha;
        this.permissao = permissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnderecoInputDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoInputDTO endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public Funcionario build() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(this.nome);
        funcionario.setCargo(this.cargo);
        funcionario.setDepartamento(this.departamento);
        funcionario.setEmail(this.email);
        funcionario.setTelefone(this.telefone);
        funcionario.setSenha(this.senha);
        funcionario.setEndereco(this.endereco.build());
        return funcionario;
    }
}
