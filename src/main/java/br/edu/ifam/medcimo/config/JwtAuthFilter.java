package br.edu.ifam.medcimo.config;

import br.edu.ifam.medcimo.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger; // <-- 1. IMPORT CORRETO (SLF4J)
import org.slf4j.LoggerFactory; // <-- 2. IMPORT CORRETO (SLF4J)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    // 3. DEFINIÇÃO CORRETA DO LOGGER (como no DataMigrationRunner)
    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserDetailsService userDetailsService; // (Este é o seu FuncionarioService)

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            // 1. Pega o token do cabeçalho
            String token = extractToken(request);

            // 2. Valida o token
            if (token != null && jwtTokenService.validateToken(token)) {

                // 3. Se for válido, busca o usuário no banco
                String email = jwtTokenService.getEmailFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // 4. Cria a autenticação
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // 5. Define o usuário como "autenticado" para esta requisição
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // 4. AGORA ESTA LINHA FUNCIONARÁ
            // E você verá no log do backend se o token for inválido
            log.error("Não foi possível autenticar o usuário: " + e.getMessage());
        }

        // 6. Continua a cadeia de filtros (mesmo se a autenticação falhar)
        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token "Bearer" do cabeçalho Authorization.
     */
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7); // Retorna apenas a string do token
        }
        return null;
    }
}