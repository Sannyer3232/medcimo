package br.edu.ifam.medcimo.dto;


import br.edu.ifam.medcimo.model.Permissao;
import br.edu.ifam.medcimo.repository.FuncionarioRepository;


public class PermissaoInputDTO {


    private String nivelAcesso;
    private String descricao;
    private long funcionario;

    public PermissaoInputDTO() {
    }

    public PermissaoInputDTO(String nivelAcesso, String descricao, long funcionario) {

        this.nivelAcesso = nivelAcesso;
        this.descricao = descricao;
        this.funcionario = funcionario;
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

    public long getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(long funcionario) {
        this.funcionario = funcionario;
    }

    public Permissao build(FuncionarioRepository funcionarioRepository) {
        Permissao permissao = new Permissao();
        permissao.setDescricao(this.descricao);
        permissao.setNivelAcesso(this.nivelAcesso);
        permissao.setFuncionario(funcionarioRepository.findById(this.funcionario).get());

        return permissao;
    }
}
