package med.voll.api.domain.consulta.agenda.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.agenda.DTOAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DTOAgendarConsulta datos){

        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);

        var pacienteConConsulta = consultaRepository.existsByPacienteIdAndCanceladaFalseAndFechaBetween(datos.idPaciente(),primerHorario,ultimoHorario);
        if(pacienteConConsulta){
            throw new ValidationException("El paciente ya tiene una consulta en ese dia");
        }
    }

}
