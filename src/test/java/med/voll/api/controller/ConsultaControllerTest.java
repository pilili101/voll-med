package med.voll.api.controller;

import med.voll.api.domain.consulta.agenda.AgendaConsultaService;
import med.voll.api.domain.consulta.agenda.DTOAgendarConsulta;
import med.voll.api.domain.consulta.agenda.DTODetallesConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {


    @Autowired
    private MockMvc mvc;

    //trasnformacion json a object y de object a json
    @Autowired
    private JacksonTester<DTOAgendarConsulta> agendarConsultaJacksonTester;
    @Autowired
    private JacksonTester<DTODetallesConsulta> detallesConsultaJacksonTester;

    @MockBean
    private AgendaConsultaService agendaConsultaService;

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Deberia retornar estado http 400 cuando los datos ingresados sean invalidos")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        //given //when
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();
        //then
        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());
    }

    @Test
    @DisplayName("Deberia retornar estado http 200 cuando los datos ingresados sean validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        //given
       var fecha = LocalDateTime.now().plusHours(1);
       var especialidad = Especialidad.CARDIOLOGIA;
       var detalles = new DTODetallesConsulta(null,8L,48L,fecha);

       // when
        when(agendaConsultaService.agendar(any())).thenReturn(detalles);
        var response = mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendarConsultaJacksonTester.write(new DTOAgendarConsulta(null, 8L,48L,fecha,especialidad)).getJson())
        ).andReturn().getResponse();

        //then
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        var jsonEsperado = detallesConsultaJacksonTester.write(detalles).getJson();
        assertEquals(response.getContentAsString(),jsonEsperado);
    }
}







