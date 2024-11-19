package school.sptech.projetoconsultorio.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import school.sptech.projetoconsultorio.entity.Consulta;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {

    /*
        Busca de uma consulta entre duas datas
        findBy = SELECT c FROM Consulta
        dataAgendamentoBetween =
            WHERE dataAgendamento >= '2024-04-17' AND dataAgendamento <= '2024-05-17'
     */
    List<Consulta> findByDataAgendamentoBetween(LocalDate dataInicio, LocalDate dataFim);

    // JPQL - Java Persistence Query Language
    @Query("SELECT c FROM Consulta c WHERE c.dataAgendamento >= :dataInicio " +
            "AND c.dataAgendamento <= :dataFim")
    List<Consulta> buscaEntreDatas(LocalDate dataInicio, LocalDate dataFim);

    // Busca por nome contendo parte do nome e ignorando case
    List<Consulta> findByNomeContainsIgnoreCase(String nome);
    // Método equivalente ao de cima usando JPQL
    @Query("SELECT c FROM Consulta c WHERE c.nome ilike %:nome%")
    List<Consulta> buscaPorParteDoNome(String nome);

    // Busca consultas por id do médico que está relacionado a consulta
    List<Consulta> findByMedicoId(int id);
    // Método equivalente ao de cima usando JPQL
    @Query("SELECT c FROM Consulta c WHERE c.medico.id = :id")
    List<Consulta> buscaPorMedicoId(int id);

    // Busca consultas por id do médico e entre duas datas
    List<Consulta> findByMedicoIdAndDataAgendamentoBetween(int id, LocalDate dataInicio, LocalDate dataFim);


    @Modifying
    @Transactional
    @Query("UPDATE Consulta c SET c.nome = :nome WHERE c.id = :id")
    void atualizarNome(int id, String nome);
}
