package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta,Long> {

    Boolean existsByPacienteIdAndCanceladaFalseAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndFechaAndCanceladaFalse(Long idMedico, LocalDateTime fecha);

    Page<Consulta> findByCanceladaFalse(Pageable paginacion);
}
