package mayorista.proveedores;

import mayorista.proveedores.Model.ProveedoresModel;
import mayorista.proveedores.Repository.ProveedoresRepository;
import mayorista.proveedores.Service.ProveedoresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProveedoresServiceTest {

    @Mock
    private ProveedoresRepository proveedoresRepository;

    @InjectMocks
    private ProveedoresService proveedoresService;

    private ProveedoresModel proveedor;

    @BeforeEach
    void setUp() {
        proveedor = new ProveedoresModel();
        proveedor.setNombre("Dulces del Sur");
        proveedor.setCorreo("contacto@dulcesdelsur.cl");
        proveedor.setTelefono("987654321");
        proveedor.setDireccion("Concepcion");
        proveedor.setRutRuc("76123456-7");
        proveedor.setProductosQueProvee("gomitas, chocolates");
    }

    @Test
    void getAllProveedores_retornaLista() {
        when(proveedoresRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        List<ProveedoresModel> resultado = proveedoresService.getAllProveedores();
        assertEquals(1, resultado.size());
        verify(proveedoresRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_cuandoExiste_retornaProveedor() {
        when(proveedoresRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        Optional<ProveedoresModel> resultado = proveedoresService.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Dulces del Sur", resultado.get().getNombre());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_retornaVacio() {
        when(proveedoresRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<ProveedoresModel> resultado = proveedoresService.obtenerPorId(99L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void crearProveedor_correoYaExiste_lanzaExcepcion() {
        when(proveedoresRepository.existsByCorreo("contacto@dulcesdelsur.cl")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> {
            proveedoresService.crearProveedor(proveedor);
        });
    }

    @Test
    void crearProveedor_rutYaExiste_lanzaExcepcion() {
        when(proveedoresRepository.existsByCorreo("contacto@dulcesdelsur.cl")).thenReturn(false);
        when(proveedoresRepository.existsByRutRuc("76123456-7")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> {
            proveedoresService.crearProveedor(proveedor);
        });
    }

    @Test
    void crearProveedor_datosValidos_creaCorrectamente() {
        when(proveedoresRepository.existsByCorreo("contacto@dulcesdelsur.cl")).thenReturn(false);
        when(proveedoresRepository.existsByRutRuc("76123456-7")).thenReturn(false);
        when(proveedoresRepository.save(proveedor)).thenReturn(proveedor);

        ProveedoresModel resultado = proveedoresService.crearProveedor(proveedor);

        assertNotNull(resultado);
        verify(proveedoresRepository, times(1)).save(proveedor);
    }

    @Test
    void actualizarProveedor_cuandoNoExiste_lanzaExcepcion() {
        when(proveedoresRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            proveedoresService.actualizarProveedor(99L, proveedor);
        });
    }

    @Test
    void eliminarProveedor_cuandoNoExiste_lanzaExcepcion() {
        when(proveedoresRepository.existsById(99L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> {
            proveedoresService.eliminarProveedor(99L);
        });
    }

    @Test
    void eliminarProveedor_cuandoExiste_eliminaCorrectamente() {
        when(proveedoresRepository.existsById(1L)).thenReturn(true);
        doNothing().when(proveedoresRepository).deleteById(1L);
        assertDoesNotThrow(() -> proveedoresService.eliminarProveedor(1L));
        verify(proveedoresRepository, times(1)).deleteById(1L);
    }
}