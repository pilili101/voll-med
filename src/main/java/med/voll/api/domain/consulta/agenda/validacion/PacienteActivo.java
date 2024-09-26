package med.voll.api.domain.consulta.agenda.validacion;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.agenda.DTOAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DTOAgendarConsulta datos){
        if(datos.idPaciente()==null){
            return;
        }
        var pacienteActivo = pacienteRepository.findActivoById(datos.idPaciente());
        if(!pacienteActivo){
            throw new ValidationException("No se puede permitir agendar citas con pacientes inactivos");
        }
    }

}
