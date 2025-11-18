package br.edu.ifam.medcimo.controler;

import br.edu.ifam.medcimo.dto.FuncionarioOutputDTO;
import br.edu.ifam.medcimo.dto.LoginDTO;
import br.edu.ifam.medcimo.dto.LoginResponseDTO;
import br.edu.ifam.medcimo.model.Funcionario;
import br.edu.ifam.medcimo.repository.FuncionarioRepository;
import br.edu.ifam.medcimo.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService; // (Você precisará criar este serviço)

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // Valida o usuário e senha
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
            );

            // Se for válido, pega os dados do usuário
            Funcionario funcionario = funcionarioRepository.findByEmail(loginDTO.getEmail()).get();
            // Gera o token JWT
            String token = jwtTokenService.generateToken(funcionario);

            // Retorna o token e os dados do usuário para o frontend
            return ResponseEntity.ok(new LoginResponseDTO(token, new FuncionarioOutputDTO(funcionario)));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }
}
