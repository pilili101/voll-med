package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DTODireccion;

public record DTOActualizarMedico(
        @NotNull Long id,
        String nombre,
        String documento,
        DTODireccion direccion
) {
}
