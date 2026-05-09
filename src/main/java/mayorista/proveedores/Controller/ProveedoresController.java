package mayorista.proveedores.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import mayorista.proveedores.Service.ProveedoresService;
import mayorista.proveedores.Model.ProveedoresModel;

import java.util.List;
import java.util.Map;

@RestController //indica que esta clase maneja peticiones HTTP y retorna JSON
@RequestMapping("/proveedores") //todas las rutas de esta clase empiezan con /proveedores
public class ProveedoresController {

    //inyectamos el service para usar la lógica de negocio
    private final ProveedoresService proveedoresService;

    //constructor — Spring inyecta el service automáticamente
    public ProveedoresController(ProveedoresService proveedoresService) {
        this.proveedoresService = proveedoresService;
    }

    //get/proveedores — retorna todos los proveedores
    @Operation(summary = "Obtener todos los proveedores", description = "Devuelve una lista de todos los proveedores registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<ProveedoresModel>> getAllProveedores() {
        return ResponseEntity.ok(proveedoresService.getAllProveedores()); // retorna 200 con la lista
    }

    //get/proveedores/{id} — busca un proveedor por su id
    @Operation(summary = "Obtener proveedor por ID")
    @ApiResponse(responseCode = "200", description = "Proveedor encontrado")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ProveedoresModel> obtenerPorId(@PathVariable Long id) {
        return proveedoresService.obtenerPorId(id)
                .map(ResponseEntity::ok) // si existe retorna 200 con el proveedor
                .orElseGet(() -> ResponseEntity.notFound().build()); // si no existe retorna 404
    }

    //get/proveedores/buscar?correo=xxx — busca un proveedor por correo
    @Operation(summary = "Buscar proveedor por correo")
    @ApiResponse(responseCode = "200", description = "Proveedor encontrado")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    @GetMapping("/buscar")
    public ResponseEntity<ProveedoresModel> buscarPorCorreo(@RequestParam String correo) {
        return proveedoresService.buscarPorCorreo(correo)
                .map(ResponseEntity::ok) // si existe retorna 200 con el proveedor
                .orElseGet(() -> ResponseEntity.notFound().build()); // si no existe retorna 404
    }

    //get/proveedores/rut?rutRuc=xxx — busca un proveedor por RUT/RUC
    @Operation(summary = "Buscar proveedor por RUT/RUC")
    @ApiResponse(responseCode = "200", description = "Proveedor encontrado")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    @GetMapping("/rut")
    public ResponseEntity<ProveedoresModel> buscarPorRutRuc(@RequestParam String rutRuc) {
        return proveedoresService.buscarPorRutRuc(rutRuc)
                .map(ResponseEntity::ok) // si existe retorna 200 con el proveedor
                .orElseGet(() -> ResponseEntity.notFound().build()); // si no existe retorna 404
    }

    //post/proveedores — crea un nuevo proveedor
    @Operation(summary = "Crear proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado correctamente"),
            @ApiResponse(responseCode = "409", description = "El proveedor ya existe"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<?> crearProveedor(@Valid @RequestBody ProveedoresModel proveedor) {
        // @Valid activa las validaciones del Model (@NotBlank, @Email, etc.)
        // @RequestBody convierte el JSON que llega en un objeto ProveedoresModel
        try {
            ProveedoresModel creado = proveedoresService.crearProveedor(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado); // retorna 201
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", e.getMessage())); // retorna 409 si ya existe
        }
    }

    //put/proveedores/{id} — actualiza un proveedor existente
    @Operation(summary = "Actualizar proveedor")
    @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProveedor(@PathVariable Long id, @RequestBody ProveedoresModel proveedor) {
        try {
            return ResponseEntity.ok(proveedoresService.actualizarProveedor(id, proveedor)); // retorna 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // si no existe retorna 404
        }
    }

    //delete/proveedores/{id} — elimina un proveedor existente
    @Operation(summary = "Eliminar proveedor")
    @ApiResponse(responseCode = "200", description = "Proveedor eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        try {
            proveedoresService.eliminarProveedor(id);
            return ResponseEntity.ok(Map.of("mensaje", "Proveedor eliminado exitosamente")); // retorna 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // si no existe retorna 404
        }
    }
}