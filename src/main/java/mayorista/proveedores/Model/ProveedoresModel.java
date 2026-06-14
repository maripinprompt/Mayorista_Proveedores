package mayorista.proveedores.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedoresModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore // el id lo genera MySQL automaticamente, no se acepta ni se muestra en el JSON
    private Long id;

    @NotBlank // valida que el nombre no venga vacio
    private String nombre;

    @Email // valida que tenga formato de correo
    @NotBlank // valida que no venga vacio
    @Column(unique = true) // no pueden existir dos proveedores con el mismo correo
    private String correo;

    private String telefono;
    private String direccion;

    @Column(unique = true) // no pueden existir dos proveedores con el mismo RUT/RUC
    private String rutRuc;

    private String productosQueProvee;
}