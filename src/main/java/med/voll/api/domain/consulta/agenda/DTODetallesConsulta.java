package med.voll.api.domain.consulta.agenda;

import med.voll.api.domain.consulta.Consulta;

import java.time.LocalDateTime;

public record DTODetallesConsulta(Long id,
                                  Long idPaciente,
                                  Long idMedico,
                                  LocalDateTime fecha) {

    public DTODetallesConsulta(Consulta consulta) {
        this(consulta.getId(),consulta.getPaciente().getId(),consulta.getMedico().getId(),consulta.getFecha());
    }
}
