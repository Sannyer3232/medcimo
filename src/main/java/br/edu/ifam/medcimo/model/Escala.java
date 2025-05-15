package br.edu.ifam.medcimo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Escala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private String turno;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    public Escala() {
    }

    public Escala(long id, LocalDate data, String turno, Funcionario funcionario) {
        this.id = id;
        this.data = data;
        this.turno = turno;
        this.funcionario = funcionario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
