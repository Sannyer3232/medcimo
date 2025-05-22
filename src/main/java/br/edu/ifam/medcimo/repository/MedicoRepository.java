package br.edu.ifam.medcimo.repository;

import br.edu.ifam.medcimo.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    @Query("SELECT m FROM Medico m where m.crm = :crm")
    Medico findMedicoByCrm(@Param("crm") String crm);
}
