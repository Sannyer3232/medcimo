package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Escala;
import br.edu.ifam.medcimo.model.Funcionario;
import br.edu.ifam.medcimo.repository.FuncionarioRepository;

import java.time.LocalDate;

public class EscalaInputDTO {

    private LocalDate data;
    private String turno;

    private long funcionarioId;

    public EscalaInputDTO() {}

    public EscalaInputDTO(LocalDate data, String turno, long funcionarioId) {
        this.data = data;
        this.turno = turno;
        this.funcionarioId = funcionarioId;
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

    public long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Escala build(FuncionarioRepository funcionarioRepository){
        Escala escala = new Escala();
        escala.setData(this.data);
        escala.setTurno(this.turno);
        escala.setFuncionario(funcionarioRepository.findById(this.funcionarioId).get());
        return escala;
    }
}
