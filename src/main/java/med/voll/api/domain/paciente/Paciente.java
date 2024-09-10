package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String documento;
    private String telefono;
    private Boolean activo;

    @Embedded
    private Direccion direccion;

    public Paciente(DTORegistrarPaciente datos) {
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.telefono = datos.telefono();
        this.documento = datos.telefono();
        this.activo = true;
        this.direccion = new Direccion(datos.direccion());
    }

    public void actualizarPaciente(DTOActualizarPaciente dtoActualizarPaciente) {
        if (dtoActualizarPaciente.nombre()!=null){
            this.nombre=dtoActualizarPaciente.nombre();
        }
        if (dtoActualizarPaciente.documento()!=null){
            this.documento=dtoActualizarPaciente.documento();
        }
        if (dtoActualizarPaciente.direccion()!=null){
            this.direccion= direccion.actualizarDatos(dtoActualizarPaciente.direccion());
        }
    }

    public void desactivarPaciente(){
        this.activo=false;
    }
}
