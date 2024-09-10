package med.voll.api.domain.medico;

public record DTOListarMedicos(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email
) {
    public DTOListarMedicos(Medico medico) {
        this(medico.getId(),
                medico.getNombre(),
                medico.getEspecialidad().toString(),
                medico.getDocumento(),
                medico.getEmail());
    }
}
