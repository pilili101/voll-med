package med.voll.api.domain.consulta.cancelacion;

import jakarta.validation.constraints.NotNull;

public record DTOCancelarConsulta(
        Long idConsulta,
        MotivoCancelacion motivoCancelacion
) {
}
