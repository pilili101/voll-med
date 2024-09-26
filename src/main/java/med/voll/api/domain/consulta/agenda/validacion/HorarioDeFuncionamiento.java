package med.voll.api.domain.consulta.agenda.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.agenda.DTOAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamiento implements  ValidadorDeConsultas{

    public void validar(DTOAgendarConsulta datos){
        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
        var antesDeApertura = datos.fecha().getHour()<7;
        var despuesDeCierre = datos.fecha().getHour()>18;

        if(domingo || antesDeApertura || despuesDeCierre){
            throw new ValidationException("El horario de atencion es de 7 a 19, de lunes a sabados");
        }
    }

}
