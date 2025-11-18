package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Funcionario;
import br.edu.ifam.medcimo.model.Permissao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FuncionarioOutputDTO {

    private long id;
    private String nome;
    private String cargo;
    private String departamento;
    private String email;
    private String telefone;
    private EnderecoOutputDTO endereco;

    private List<String> permissoes;

    public FuncionarioOutputDTO() {
    }

    public FuncionarioOutputDTO(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.cargo = funcionario.getCargo();
        this.departamento = funcionario.getDepartamento();
        this.email = funcionario.getEmail();
        this.telefone = funcionario.getTelefone();
        this.endereco = new EnderecoOutputDTO(funcionario.getEndereco());

        if(funcionario.getPermissoes() != null) {
            this.permissoes = funcionario.getPermissoes().stream()
                    .map(Permissao::getNivelAcesso)
                    .collect(Collectors.toList());
        }else{
            this.permissoes = new ArrayList<>();
        }
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

    public EnderecoOutputDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoOutputDTO endereco) {
        this.endereco = endereco;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<String> permissoes) {
        this.permissoes = permissoes;
    }
}
