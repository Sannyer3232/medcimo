package br.edu.ifam.medcimo.service;

import br.edu.ifam.medcimo.model.Funcionario;
import br.edu.ifam.medcimo.model.Permissao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms}")
    private long jwtExpiration;

    public String generateToken(Funcionario funcionario) {

        Date now = new Date();

        Date expiration = new Date(now.getTime() + jwtExpiration);

        SecretKey key = getSigningKey();

        List<String> authorities = funcionario.getPermissoes().stream()
                .map(Permissao::getNivelAcesso)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(funcionario.getEmail())
                .claim("roles", authorities)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Extrai as permissões (roles) de um token JWT.
     */
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return (List<String>) claims.get("roles");
    }


    /**
     * Converte a string secreta (do application.properties) em uma chave segura.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
