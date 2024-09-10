package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DTOAgendarConsulta;
import med.voll.api.domain.consulta.DTODetallesConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {


    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DTOAgendarConsulta datos) {
        System.out.println(datos);
        return ResponseEntity.ok(new DTODetallesConsulta(null, null, null, null));
    }
}
