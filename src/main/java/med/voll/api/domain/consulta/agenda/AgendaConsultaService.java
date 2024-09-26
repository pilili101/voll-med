package med.voll.api.domain.consulta.agenda;


import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.agenda.validacion.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;



    public DTODetallesConsulta agendar(DTOAgendarConsulta datos) {

        //validaciones simples
        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }
        if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }

        //validaciones complejas

        validadores.forEach(v->v.validar(datos));

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var medico = seleccionarMedico(datos);
        if(medico==null){
            throw new ValidacionDeIntegridad("No existen medicos diponibles para este horario y especialidad");
        }
        var consulta = new Consulta(medico, paciente, datos.fecha());
        consultaRepository.save(consulta);

        return new DTODetallesConsulta(consulta);
    }



    private Medico seleccionarMedico(DTOAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad");
        }
        return medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }

}
