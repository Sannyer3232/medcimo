package br.edu.ifam.medcimo.dto;

import br.edu.ifam.medcimo.model.Endereco;
import jakarta.persistence.Column;

public class EnderecoInputDTO {

    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public EnderecoInputDTO() {
    }

    public EnderecoInputDTO(String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Endereco build(){
        Endereco endereco = new Endereco();
        endereco.setRua(this.rua);
        endereco.setNumero(this.numero);
        endereco.setBairro(this.bairro);
        endereco.setCidade(this.cidade);
        endereco.setEstado(this.estado);
        endereco.setCep(this.cep);
        return endereco;
    }
}
