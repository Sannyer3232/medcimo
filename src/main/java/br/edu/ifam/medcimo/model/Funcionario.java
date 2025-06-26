package br.edu.ifam.medcimo.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String nome;
    @Column(nullable = false, length = 200)
    private String cargo;
    @Column(nullable = false, length = 200)
    private String departamento;
    @Column(nullable = false, length = 200, unique = true)
    private String email;
    @Column(nullable = false, length = 200, unique = true)
    private String telefone;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Escala> escalas;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<Permissao> permissoes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public Funcionario() {
    }

    public Funcionario(long id, String nome, String cargo, String departamento, String email, String telefone, List<Escala> escalas, List<Permissao> permissoes, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.departamento = departamento;
        this.email = email;
        this.telefone = telefone;
        this.escalas = escalas;
        this.permissoes = permissoes;
        this.endereco = endereco;
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

    public List<Escala> getEscalas() {
        return escalas;
    }

    public void setEscalas(List<Escala> escalas) {
        this.escalas = escalas;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
