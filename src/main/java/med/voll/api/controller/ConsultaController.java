package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.agenda.AgendaConsultaService;
import med.voll.api.domain.consulta.agenda.DTOAgendarConsulta;
import med.voll.api.domain.consulta.agenda.DTODetallesConsulta;
import med.voll.api.domain.consulta.cancelacion.CancelacionService;
import med.voll.api.domain.consulta.cancelacion.DTOCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private AgendaConsultaService agendaService;

    @Autowired
    private CancelacionService cancelacionService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DTOAgendarConsulta datos) {
        var response = agendaService.agendar(datos);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DTOCancelarConsulta datos){
        cancelacionService.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page> listarConsultas(@PageableDefault Pageable paginacion){
        return ResponseEntity.ok(repository.findByCanceladaFalse(paginacion).map(DTODetallesConsulta::new));
    }
}
