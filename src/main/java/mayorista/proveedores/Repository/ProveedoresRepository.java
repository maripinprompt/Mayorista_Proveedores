package mayorista.proveedores.Repository;

import mayorista.proveedores.Model.ProveedoresModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // marca esta interfaz como componente de acceso a datos
public interface ProveedoresRepository extends JpaRepository<ProveedoresModel, Long> {

    //busca: SELECT * FROM proveedores WHERE correo = ?
    Optional<ProveedoresModel> findByCorreo(String correo);

    //busca: SELECT * FROM proveedores WHERE rut_ruc = ?
    Optional<ProveedoresModel> findByRutRuc(String rutRuc);

    //verifica si ya existe un proveedor con ese correo
    boolean existsByCorreo(String correo);

    //verifica si ya existe un proveedor con ese RUT/RUC
    boolean existsByRutRuc(String rutRuc);
}