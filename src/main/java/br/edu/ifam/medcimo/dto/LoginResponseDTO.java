package br.edu.ifam.medcimo.dto;

public class LoginResponseDTO {
    private String token;

    // Reutilizamos o FuncionarioOutputDTO que você já tem [cite: FuncionarioService.java]
    private FuncionarioOutputDTO usuario;

    // Construtor
    public LoginResponseDTO(String token, FuncionarioOutputDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public FuncionarioOutputDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(FuncionarioOutputDTO usuario) {
        this.usuario = usuario;
    }
}
