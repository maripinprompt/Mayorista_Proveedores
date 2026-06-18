package mayorista.proveedores.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedoresModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // permite que el id se muestre en las respuestas GET, pero bloquea que se envie manualmente en POST/PUT
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    @Column(unique = true)
    private String correo;

    private String telefono;
    private String direccion;

    @Column(unique = true)
    private String rutRuc;

    private String productosQueProvee;
}