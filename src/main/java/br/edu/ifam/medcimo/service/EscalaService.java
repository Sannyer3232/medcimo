package br.edu.ifam.medcimo.service;

import br.edu.ifam.medcimo.dto.EscalaInputDTO;
import br.edu.ifam.medcimo.dto.EscalaOutputDTO;
import br.edu.ifam.medcimo.model.Escala;
import br.edu.ifam.medcimo.repository.EscalaRepository;
import br.edu.ifam.medcimo.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EscalaService {

    @Autowired
    private EscalaRepository escalaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<EscalaOutputDTO> list(){
        try {
            List<Escala> escalas = escalaRepository.findAll();
            List<EscalaOutputDTO> escalaDTOs = new ArrayList<>();

            for (Escala escala : escalas) {
                escalaDTOs.add(new EscalaOutputDTO(escala));
            }

            return escalaDTOs;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public EscalaOutputDTO getById(long id){

        try{
            return new EscalaOutputDTO(escalaRepository.findById(id).get());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public EscalaOutputDTO creat(EscalaInputDTO escalaInputDTO){
        try{
            return new EscalaOutputDTO(escalaRepository.save(escalaInputDTO.build(funcionarioRepository)));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public EscalaOutputDTO update(long id ,EscalaInputDTO escalaInputDTO){
        try{
            Escala escala = escalaRepository.findById(id).get();
            Escala escalaUpdate = escalaInputDTO.build(funcionarioRepository);

            escala.setData(escalaUpdate.getData());
            escala.setTurno(escalaUpdate.getTurno());
            escala.setFuncionario(escalaUpdate.getFuncionario());
            return new EscalaOutputDTO(escalaRepository.save(escala));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(long id){
        try {
            Escala escala = escalaRepository.findById(id).get();
            escalaRepository.delete(escala);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
