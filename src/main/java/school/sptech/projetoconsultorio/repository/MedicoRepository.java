package school.sptech.projetoconsultorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.projetoconsultorio.entity.Consulta;
import school.sptech.projetoconsultorio.entity.Medico;

import java.time.LocalDate;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {

}
