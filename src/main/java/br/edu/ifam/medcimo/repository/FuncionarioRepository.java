package br.edu.ifam.medcimo.repository;

import br.edu.ifam.medcimo.model.Funcionario;
import br.edu.ifam.medcimo.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("Select f From Funcionario f Where f.nome = :nome")
    List<Funcionario> findByNome(@Param("nome") String nome);


}
