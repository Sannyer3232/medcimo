package br.edu.ifam.medcimo.service;

import br.edu.ifam.medcimo.dto.FuncionarioInputDTO;
import br.edu.ifam.medcimo.dto.FuncionarioOutputDTO;
import br.edu.ifam.medcimo.model.Funcionario;
import br.edu.ifam.medcimo.repository.EspecialidadeRepository;
import br.edu.ifam.medcimo.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;

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

    public FuncionarioOutputDTO creat(FuncionarioInputDTO funcionarioInputDTO){
        try{
            return new FuncionarioOutputDTO(funcionarioRepository.save(funcionarioInputDTO.build()));
        }catch(Exception e){
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
}
