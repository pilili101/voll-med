package med.voll.api.domain.paciente;

public record DTOListarPacientes(
        Long id,
        String nombre,
        String email,
        String documento,
        String telefono
) {
    public DTOListarPacientes(Paciente paciente){
        this(paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento(),
                paciente.getTelefono());
    }

}
