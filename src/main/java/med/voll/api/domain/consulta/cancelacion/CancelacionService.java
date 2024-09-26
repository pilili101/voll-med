package med.voll.api.domain.consulta.cancelacion;

import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.cancelacion.validacion.ValidadorCancelamiento;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CancelacionService {

    @Autowired
    List<ValidadorCancelamiento> validadorCancelamientos;

    @Autowired
    private ConsultaRepository  consultaRepository;

    public void cancelar(DTOCancelarConsulta datos){
        if(datos.idConsulta()==null){
            throw  new ValidacionDeIntegridad("Por favor ingrese el id de la consulta");

        }
        if(!consultaRepository.existsById(datos.idConsulta())){
            throw  new ValidacionDeIntegridad("El id de la consulta no existe");
        }
        if(datos.motivoCancelacion()==null){
            throw  new ValidacionDeIntegridad("Debe ingresar un motivo de cancelacion");
        }
        validadorCancelamientos.forEach(v->v.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivoCancelacion());
    }

}
