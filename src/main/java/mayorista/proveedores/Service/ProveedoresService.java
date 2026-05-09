package mayorista.proveedores.Service;

import mayorista.proveedores.Model.ProveedoresModel;
import mayorista.proveedores.Repository.ProveedoresRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // marca esta clase como componente de lógica de negocio
public class ProveedoresService {

    // inyectamos el repository para poder acceder a la base de datos
    private final ProveedoresRepository proveedoresRepository;

    // constructor — Spring inyecta el repository automáticamente
    public ProveedoresService(ProveedoresRepository proveedoresRepository) {
        this.proveedoresRepository = proveedoresRepository;
    }

    // retorna todos los proveedores de la base de datos
    public List<ProveedoresModel> getAllProveedores() {
        return proveedoresRepository.findAll();
    }

    // busca un proveedor por su id, retorna Optional porque puede no existir
    public Optional<ProveedoresModel> obtenerPorId(Long id) {
        return proveedoresRepository.findById(id);
    }

    // busca un proveedor por su correo
    public Optional<ProveedoresModel> buscarPorCorreo(String correo) {
        return proveedoresRepository.findByCorreo(correo);
    }

    // busca un proveedor por su RUT/RUC
    public Optional<ProveedoresModel> buscarPorRutRuc(String rutRuc) {
        return proveedoresRepository.findByRutRuc(rutRuc);
    }

    // crea un nuevo proveedor, verificando que el correo y RUT/RUC no existan
    public ProveedoresModel crearProveedor(ProveedoresModel proveedor) {
        if (proveedoresRepository.existsByCorreo(proveedor.getCorreo())) {
            throw new IllegalArgumentException("El correo ya existe");
        }
        if (proveedoresRepository.existsByRutRuc(proveedor.getRutRuc())) {
            throw new IllegalArgumentException("El RUT/RUC ya existe");
        }
        // si no existe, guarda el proveedor en la base de datos
        return proveedoresRepository.save(proveedor);
    }

    // actualiza los datos de un proveedor existente
    public ProveedoresModel actualizarProveedor(Long id, ProveedoresModel datos) {
        // busca el proveedor, si no existe lanza excepción
        ProveedoresModel proveedor = proveedoresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        // reemplaza los datos viejos con los nuevos
        proveedor.setNombre(datos.getNombre());
        proveedor.setCorreo(datos.getCorreo());
        proveedor.setTelefono(datos.getTelefono());
        proveedor.setDireccion(datos.getDireccion());
        proveedor.setRutRuc(datos.getRutRuc());
        proveedor.setProductosQueProvee(datos.getProductosQueProvee());

        // guarda el proveedor actualizado en la base de datos
        return proveedoresRepository.save(proveedor);
    }

    // elimina un proveedor por su id
    public void eliminarProveedor(Long id) {
        // verifica que el proveedor exista antes de intentar eliminarlo
        if (!proveedoresRepository.existsById(id)) {
            throw new IllegalArgumentException("Proveedor no encontrado");
        }
        proveedoresRepository.deleteById(id);
    }
}