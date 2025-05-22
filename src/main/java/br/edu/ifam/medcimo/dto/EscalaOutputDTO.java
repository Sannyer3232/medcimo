package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Escala;

import java.time.LocalDate;

public class EscalaOutputDTO {

    private long id;
    private LocalDate data;
    private String turno;
    private String funcionario;

    public EscalaOutputDTO() {
    }

    public EscalaOutputDTO(Escala escala) {
        this.id = escala.getId();
        this.data = escala.getData();
        this.turno = escala.getTurno();
        this.funcionario = escala.getFuncionario().getNome();
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

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }
}
