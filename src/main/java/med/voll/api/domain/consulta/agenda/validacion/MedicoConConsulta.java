package med.voll.api.domain.consulta.agenda.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.agenda.DTOAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements  ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DTOAgendarConsulta datos) {
        if (datos.idMedico()==null) {
            return;
        }
        var medicoConConsulta = consultaRepository.existsByMedicoIdAndFechaAndCanceladaFalse(datos.idMedico(), datos.fecha());

        if (medicoConConsulta) {
            throw new ValidationException("Este medico ya tiene una consulta en este horario");
        }
    }
}
