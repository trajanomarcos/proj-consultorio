package school.sptech.projetoconsultorio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//@NoArgsConstructor
//@AllArgsConstructor
//@Data // NÃO UTILIZAR EM ENTIDADES
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private LocalDate dataAgendamento;
    private String local;

    /*
        @ManyToOne -> Muitas consultas para um médico
        Ao utilizar a anotação @ManyToOne, estamos dizendo que a entidade Consulta possui um relacionamento com a entidade Medico.
     */
    @ManyToOne
    private Medico medico;
}