package med.voll.api.domain.consulta.agenda.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.agenda.DTOAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements  ValidadorDeConsultas{

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DTOAgendarConsulta datos){
        if(datos.idMedico()==null){
            return;
        }
        var medicoActivo = medicoRepository.findActivoById(datos.idMedico());
        if(!medicoActivo){
            throw new ValidationException("No se puede permitir agendar citas con medico inactivos");
        }
    }

}
