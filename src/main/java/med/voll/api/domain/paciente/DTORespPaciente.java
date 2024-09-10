package med.voll.api.domain.paciente;

import med.voll.api.domain.direccion.DTODireccion;

public record DTORespPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DTODireccion direccion
) {
}
