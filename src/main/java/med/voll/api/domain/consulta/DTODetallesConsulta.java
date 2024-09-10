package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DTODetallesConsulta(Long id,
                                  Long idPaciente,
                                  Long idMedico,
                                  LocalDateTime fecha) {
}
