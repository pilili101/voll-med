package med.voll.api.domain.consulta.cancelacion.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.cancelacion.DTOCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioAnticipacion implements ValidadorCancelamiento {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DTOCancelarConsulta datos){
        var consulta = repository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferencia = Duration.between(ahora,consulta.getFecha()).toHours();
        if(diferencia<24){
            throw new ValidationException("La consulta se puede cancelar con 24 horas de anticipacion");
        }
    }

}
