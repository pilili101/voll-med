package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DTODireccion;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DTORegistrarPaciente datos,
                                    UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = pacienteRepository.save(new Paciente(datos));
        DTORespPaciente dtoRespPaciente = new DTORespPaciente(paciente.getId(),paciente.getNombre(),
                paciente.getEmail(),paciente.getTelefono(),paciente.getDocumento(), new DTODireccion(paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getNumero(),paciente.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(dtoRespPaciente);
    }

    @GetMapping
    public ResponseEntity<Page> listar(@PageableDefault(size = 10)Pageable paginacion){
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DTOListarPacientes::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DTOActualizarPaciente dtoActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(dtoActualizarPaciente.id());
        paciente.actualizarPaciente(dtoActualizarPaciente);
        return ResponseEntity.ok(new DTORespPaciente(paciente.getId(),paciente.getNombre(),
                paciente.getEmail(),paciente.getTelefono(),paciente.getDocumento(), new DTODireccion(paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getNumero(),paciente.getDireccion().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity retornarMedico(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        DTORespPaciente dtoRespPaciente = new DTORespPaciente(paciente.getId(),paciente.getNombre(),
                paciente.getEmail(),paciente.getTelefono(),paciente.getDocumento(), new DTODireccion(paciente.getDireccion().getCalle(),
                paciente.getDireccion().getDistrito(), paciente.getDireccion().getCiudad(),
                paciente.getDireccion().getNumero(),paciente.getDireccion().getComplemento()));
        return ResponseEntity.ok(dtoRespPaciente);
    }

}
