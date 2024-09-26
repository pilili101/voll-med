package med.voll.api.domain.medico;

import med.voll.api.domain.direccion.DTODireccion;

public record DTORespMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        Especialidad especialidad,
        DTODireccion direccion,
        Boolean activo
) {
}
