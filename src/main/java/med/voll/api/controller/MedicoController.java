package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DTODireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    //los metodos hacen el handling del endpoint
    @PostMapping
    @Transactional
    public ResponseEntity<DTORespMedico> registrar(@RequestBody @Valid DTORegistroMedico dtoRegistroMedico,
                                    UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(dtoRegistroMedico));
        DTORespMedico dtoRespMedico = new DTORespMedico(medico.getId(),medico.getNombre(),medico.getEmail(),
                medico.getTelefono(),medico.getDocumento(),medico.getEspecialidad(), new DTODireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()),medico.getActivo());
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(dtoRespMedico);
    }

    @GetMapping
    public ResponseEntity<Page> listar(@PageableDefault(size = 10) Pageable paginacion){
//        return medicoRepository.findAll(paginacion).map(DTOListarMedicos::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DTOListarMedicos::new));

    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DTOActualizarMedico dtoActualizarMedico){
        //jpa mapea el medico que etsoy trayendo de la base de datos
        Medico medico = medicoRepository.getReferenceById(dtoActualizarMedico.id());
        // se actualizan los datos y cuando el metodo termina, la trasnaccion termina
        //termina la transaccion y se hace un commit de los cambios en la bd
        //si hay un error, transactional hace un roll back y no se hace un commit
        medico.actualizarMedico(dtoActualizarMedico);
        return ResponseEntity.ok(new DTORespMedico(medico.getId(),medico.getNombre(),medico.getEmail(),
                medico.getTelefono(),medico.getDocumento(),medico.getEspecialidad(), new DTODireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()),medico.getActivo()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTORespMedico> retornarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        DTORespMedico dtoRespMedico = new DTORespMedico(medico.getId(),medico.getNombre(),medico.getEmail(),
                medico.getTelefono(),medico.getDocumento(),medico.getEspecialidad(), new DTODireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()),medico.getActivo());
        return ResponseEntity.ok(dtoRespMedico);
    }


}
