package med.voll.api.domain.medico;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DTODireccion;

public record DTORegistroMedico(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank String telefono,
        @NotBlank @Pattern(regexp = "\\d{7,8}") String documento,
        @NotNull Especialidad especialidad,
        @NotNull @Valid DTODireccion direccion
) {
}
