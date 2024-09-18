package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico,Long> {

    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            select * from Medico m 
            where m.activo= 1 and
            m.especialidad= :especialidad and
            m.id nor in(
            select c.medico.id from Consulta c
            c.data= :fecha
            )
            orden by rand()
            limit 1
            """)
    Medico seleccionarMedicoPorEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
