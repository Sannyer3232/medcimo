package br.edu.ifam.medcimo.repository;

import br.edu.ifam.medcimo.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
