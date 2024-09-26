package med.voll.api.domain.consulta.cancelacion.validacion;

import med.voll.api.domain.consulta.cancelacion.DTOCancelarConsulta;

public interface ValidadorCancelamiento {

    public void validar(DTOCancelarConsulta datos);

}
