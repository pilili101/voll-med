package med.voll.api.domain.medico;

//import jakarta.transaction.Transactional;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DTODireccion;
import med.voll.api.domain.paciente.DTORegistrarPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //estas anotaciones son para crear una base de datos MySQL real para pruebas
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deberia retornar nulo cuando el medico se encuentra con otro paciente en ese horario")
    @Transactional
    @Commit
    void seleccionarMedicoPorEspecialidadEnFechaEscenario1() {

        //definimos la fecha
//        var proximoLunes10AM = LocalDateTime.now()
//                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
//                .withHour(10).withMinute(0).withSecond(00);


        String fecha = "2024-09-30T10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime proximoLunes10AM = LocalDateTime.parse(fecha, formatter);

        //registramos medico,paciente y consulta
        var medico = registrarMedico("Mario","mario@gmail.com","0000000","43111111",Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Luigi","luigi@gmail.com","9876778","43222222");
        registrarConsulta(medico,paciente,proximoLunes10AM);



        //hacemos la busqueda de un medico con esa especialidad y esa fecha
        var medicoLibre = medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10AM);

        assertNull(medicoLibre);
    }


    @Test
    @DisplayName("Deberia retornar medico cuando realice la consulta en la bd para ee horario")
    @Transactional
    @Commit
    void seleccionarMedicoPorEspecialidadEnFechaEscenario2() {

        //definimos la fecha
        //given
//        var proximoLunes10AM = LocalDateTime.now()
//                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
//                .withHour(10).withMinute(0).withSecond(00);

        String fecha = "2024-09-30T10:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime proximoLunes10AM = LocalDateTime.parse(fecha, formatter);

        //registramos medico,paciente y consulta
        var medico = registrarMedico("Mario2","mario2@gmail.com","0000000","43111111",Especialidad.CARDIOLOGIA);

        //hacemos la busqueda de un medico con esa especialidad y esa fecha
        //when
        var medicoLibre = medicoRepository.seleccionarMedicoPorEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10AM);
        //then
        assertEquals(medico,medicoLibre);
    }



    @Transactional
    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        var consulta = new Consulta(medico, paciente, fecha);
        em.persist(consulta);
    }

    @Transactional
    private Medico registrarMedico(String nombre, String email, String telefono, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, telefono, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    @Transactional
    private Paciente registrarPaciente(String nombre, String email, String telefono, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, telefono, documento));
        em.persist(paciente);
        return paciente;
    }

    private DTORegistroMedico datosMedico(String nombre, String email, String telefono, String documento, Especialidad especialidad) {
        return new DTORegistroMedico(
                nombre,
                email,
                documento,
                telefono,
                especialidad,
                datosDireccion()
        );
    }

    private DTORegistrarPaciente datosPaciente(String nombre, String email, String telefono, String documento) {
        return new DTORegistrarPaciente(
                nombre,
                email,
                telefono,
                documento,
                datosDireccion()
        );
    }

    private DTODireccion datosDireccion() {
        return new DTODireccion(
                " loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );
    }


}