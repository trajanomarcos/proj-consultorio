package school.sptech.projetoconsultorio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.projetoconsultorio.dto.ConsultaCriacaoDto;
import school.sptech.projetoconsultorio.entity.Consulta;
import school.sptech.projetoconsultorio.entity.Medico;
import school.sptech.projetoconsultorio.repository.ConsultaRepository;
import school.sptech.projetoconsultorio.repository.MedicoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;

    @GetMapping
    public ResponseEntity<List<Consulta>> listar() {
        List<Consulta> consultas = repository.findAll();

        if (consultas.isEmpty()) {
//            return ResponseEntity.status(204).build();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(consultas);
    }

    @PostMapping
    public ResponseEntity<Consulta> criar(@RequestBody ConsultaCriacaoDto dto) {
        Optional<Medico> medicoOpt = medicoRepository.findById(dto.getMedicoId());

        if (medicoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Consulta consulta = Consulta.builder()
                .nome(dto.getNome())
                .local(dto.getLocal())
                .dataAgendamento(dto.getDataAgendamento())
                .medico(medicoOpt.get())
                .build();

        Consulta consultaSalva = repository.save(consulta);
        return ResponseEntity.created(null).body(consultaSalva);
    }

    @GetMapping("/filtro-data")
    public ResponseEntity<List<Consulta>> porData(@RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFim) {
//        List<Consulta> consultas = repository.findByDataAgendamentoBetween(dataInicio, dataFim);
        List<Consulta> consultas = repository.buscaEntreDatas(dataInicio, dataFim);

        if (consultas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/filtro-nome")
    public ResponseEntity<List<Consulta>> porNome(@RequestParam String nome) {
//        List<Consulta> consultas = repository.findByNomeContainsIgnoreCase(nome);
        List<Consulta> consultas = repository.buscaPorParteDoNome(nome);

        if (consultas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/filtro-medico/{medicoId}")
    public ResponseEntity<List<Consulta>> porMedicoId(@PathVariable int medicoId) {
//        List<Consulta> consultas = repository.findByMedicoId(medicoId);
        List<Consulta> consultas = repository.buscaPorMedicoId(medicoId);

        if (consultas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/filtro-medico-data/{medicoId}")
    public ResponseEntity<List<Consulta>> porMedicoIdEData(@PathVariable int medicoId, @RequestParam LocalDate dataInicio, @RequestParam LocalDate dataFim) {
        List<Consulta> consultas = repository.findByMedicoIdAndDataAgendamentoBetween(medicoId, dataInicio, dataFim);

        if (consultas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consultas);
    }

    @PatchMapping("/atualizar-nome/{id}")
    public ResponseEntity<Void> atualizarNome(@PathVariable int id, @RequestParam String nome) {
        // Se não existir a consulta com o id informado, retorna 404
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // Atualiza o nome da consulta via banco
        repository.atualizarNome(id, nome);

        // Retorna 204 - No Content
        return ResponseEntity.noContent().build();
    }
}
