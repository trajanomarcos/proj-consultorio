package school.sptech.projetoconsultorio.dto;

import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import school.sptech.projetoconsultorio.entity.Medico;

import java.time.LocalDate;
@Data
@Builder
public class ConsultaCriacaoDto {

    private String nome;
    private LocalDate dataAgendamento;
    private String local;
    private Integer medicoId;
}
