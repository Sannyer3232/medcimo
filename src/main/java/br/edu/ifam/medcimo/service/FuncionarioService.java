package br.edu.ifam.medcimo.service;

import br.edu.ifam.medcimo.dto.FuncionarioInputDTO;
import br.edu.ifam.medcimo.dto.FuncionarioOutputDTO;
import br.edu.ifam.medcimo.model.Funcionario;
import br.edu.ifam.medcimo.model.Permissao;
import br.edu.ifam.medcimo.repository.EspecialidadeRepository;
import br.edu.ifam.medcimo.repository.FuncionarioRepository;
import br.edu.ifam.medcimo.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<FuncionarioOutputDTO> listaFuncionario() {
        try{
            List<Funcionario> funcionarios = funcionarioRepository.findAll();
            List<FuncionarioOutputDTO> funcionarioOutputDTOS= new ArrayList<>();
            for (Funcionario funcionario : funcionarios) {
                funcionarioOutputDTOS.add(new FuncionarioOutputDTO(funcionario));
            }

            return funcionarioOutputDTOS;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public FuncionarioOutputDTO findFuncionarioById(Long id) {
        try{
            return new FuncionarioOutputDTO(funcionarioRepository.findById(id).get());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public FuncionarioOutputDTO creat(FuncionarioInputDTO dto) {
        try {
            Funcionario funcionario = dto.build();
            funcionario.setSenha(passwordEncoder.encode(dto.getSenha()));

            // 1. Salva o funcionário primeiro para gerar o ID
            Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

            // 2. Define a permissão (Se vier nulo, define o padrão ROLE_FUNCIONARIO)
            String nivelAcesso = (dto.getPermissao() != null && !dto.getPermissao().isEmpty())
                    ? dto.getPermissao()
                    : "ROLE_FUNCIONARIO"; // Permissão Padrão

            // 3. Cria e salva a permissão
            Permissao p = new Permissao();
            p.setNivelAcesso(nivelAcesso);
            p.setDescricao("Permissão inicial");
            p.setFuncionario(funcionarioSalvo);

            permissaoRepository.save(p); //

            // Retorna o DTO (que agora vai incluir a permissão automaticamente se você ajustou o OutputDTO)
            return new FuncionarioOutputDTO(funcionarioSalvo);

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FuncionarioOutputDTO update(Long id, FuncionarioInputDTO funcionarioInputDTO){
        try {
            Funcionario funcionarioNoBD = funcionarioRepository.findById(id).get();
            Funcionario funcionarioUpdate = funcionarioInputDTO.build();
            funcionarioNoBD.setNome(funcionarioUpdate.getNome());
            funcionarioNoBD.setEmail(funcionarioUpdate.getEmail());
            funcionarioNoBD.setEndereco(funcionarioUpdate.getEndereco());
            funcionarioNoBD.setCargo(funcionarioUpdate.getCargo());
            funcionarioNoBD.setDepartamento(funcionarioUpdate.getDepartamento());
            funcionarioNoBD.setTelefone(funcionarioUpdate.getTelefone());

            if (funcionarioInputDTO.getPermissao() != null && !funcionarioInputDTO.getPermissao().isEmpty()) {

                // Opção A: Apagar as antigas e criar nova (Mais simples)
                // Você precisa adicionar um método no seu Repository para isso funcionar bem,
                // ou iterar e deletar. Exemplo simples iterando:
                List<Permissao> permissoesAntigas = funcionarioNoBD.getPermissoes();
                if (permissoesAntigas != null) {
                    permissaoRepository.deleteAll(permissoesAntigas);
                }

                // Cria a nova
                Permissao novaPermissao = new Permissao();
                novaPermissao.setNivelAcesso(funcionarioInputDTO.getPermissao());
                novaPermissao.setDescricao("Alterado via Update");
                novaPermissao.setFuncionario(funcionarioNoBD);
                permissaoRepository.save(novaPermissao);
            }

            return new FuncionarioOutputDTO(funcionarioRepository.save(funcionarioNoBD));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try{
            Funcionario funcionarioNoBD = funcionarioRepository.findById(id).get();
            funcionarioRepository.delete(funcionarioNoBD);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        List<GrantedAuthority> authorities = funcionario.getPermissoes().stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNivelAcesso()))
                .collect(Collectors.toList());

        return new User(funcionario.getEmail(), funcionario.getSenha(), authorities);
    }
}
