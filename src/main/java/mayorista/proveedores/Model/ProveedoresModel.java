package mayorista.proveedores.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity // le dice a Spring que esta clase representa una tabla en la base de datos
@Table(name = "proveedores") // el nombre que tendrá la tabla en MySQL
@Data // Lombok genera automáticamente getters, setters, toString, equals y hashCode
@NoArgsConstructor // Lombok genera un constructor vacío (requerido por JPA)
@AllArgsConstructor // Lombok genera un constructor con todos los campos
public class ProveedoresModel {

    @Id // indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // el id se genera automáticamente y se incrementa solo
    private Long id;

    @NotBlank // valida que el nombre no venga vacío
    private String nombre;

    @Email // valida que tenga formato de correo
    @NotBlank // valida que no venga vacío
    @Column(unique = true) // no pueden existir dos proveedores con el mismo correo
    private String correo;

    private String telefono; // campo opcional
    private String direccion; // campo opcional

    @Column(unique = true) // no pueden existir dos proveedores con el mismo RUT/RUC
    private String rutRuc;

    private String productosQueProvee; // ej: "gomitas, chocolates, caramelos"
}