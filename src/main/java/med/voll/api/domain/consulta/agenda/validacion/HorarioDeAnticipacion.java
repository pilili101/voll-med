package med.voll.api.domain.consulta.agenda.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.agenda.DTOAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{

    public void validar(DTOAgendarConsulta datos){
        var ahora = LocalDateTime.now();
        var horaConsulta = datos.fecha();
        var diferencia = Duration.between(ahora,horaConsulta).toMinutes()<30;
        if(diferencia){
            throw  new ValidationException("La consulta debe ser agendada con al menos 30 min de anticipacion");
        }
    }

}
