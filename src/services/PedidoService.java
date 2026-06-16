/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;


import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exception.EntidadNoEncontradaException;
import exception.ReglaNegocioException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Xavier
 */
public class PedidoService {

    private List<Pedido> pedidos;
    // IMPORTANTE: Inyectamos los servicios que necesitamos consultar
    private UsuarioService usuarioService;
    private ProductoService productoService;

    public PedidoService(UsuarioService usuarioService, ProductoService productoService) {
        this.pedidos = new ArrayList<>();
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    // 1. LISTAR (HU-PED-01)
    public void listarPedidos() {
        boolean hayPedidos = false;
        System.out.println("\n--- LISTADO DE PEDIDOS ---");
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                System.out.println("ID: " + p.getId() + " | Fecha: " + p.getFecha() + 
                                   " | Usuario: " + p.getUsuario().getNombre() + 
                                   " | Estado: " + p.getEstado() + " | Pago: " + p.getFormaPago() + 
                                   " | Total: $" + p.getTotal());
                hayPedidos = true;
            }
        }
        if (!hayPedidos) {
            System.out.println("No hay pedidos registrados o activos.");
        }
    }

    // MÉTODO AUXILIAR: Buscar por ID
    public Pedido buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró un pedido activo con el ID: " + id);
    }

    // --- FLUJO DE CREACIÓN DE PEDIDO (HU-PED-02) ---

    // Paso A: Iniciar el pedido en memoria (Aún no se guarda en la lista general)
    public Pedido iniciarPedido(Long idUsuario, FormaPago formaPago) throws EntidadNoEncontradaException {
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        // Creamos el pedido con la fecha actual y estado PENDIENTE por defecto
        return new Pedido(LocalDate.now(), Estado.PENDIENTE, formaPago, usuario);
    }

    // Paso B: Agregar detalles al pedido iterativamente
    public void agregarDetalle(Pedido pedidoTemporal, Long idProducto, int cantidad) 
                               throws EntidadNoEncontradaException, ReglaNegocioException {
        
        if (cantidad <= 0) {
            throw new ReglaNegocioException("La cantidad debe ser mayor a 0.");
        }

        Producto producto = productoService.buscarPorId(idProducto);

        if (producto.getStock() < cantidad) {
            throw new ReglaNegocioException("Stock insuficiente. Stock actual de " + producto.getNombre() + ": " + producto.getStock());
        }

        // Descontamos el stock físicamente
        producto.setStock(producto.getStock() - cantidad);

        // Cumplimos la regla estricta de la cátedra: usar el método de la entidad
        pedidoTemporal.addDetallePedido(cantidad, producto);
    }

    // Paso C (Éxito): Guardar el pedido final
    public void confirmarYGuardarPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.getUsuario().agregarPedido(pedido); // Mantenemos la relación bidireccional
    }

    // Paso C (Fallo): Si algo sale mal en el menú, devolvemos el stock
    public void cancelarPedidoYRevertirStock(Pedido pedidoTemporal) {
        for (DetallePedido dp : pedidoTemporal.getDetallePedidos()) {
            Producto p = dp.getProducto();
            // Le devolvemos al producto la cantidad que le habíamos restado
            p.setStock(p.getStock() + dp.getCantidad());
        }
    }

    // 3. EDITAR ESTADO Y PAGO (HU-PED-03)
    public void actualizarEstadoYPago(Long id, Estado nuevoEstado, FormaPago nuevaFormaPago) 
                                      throws EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(id);
        pedido.setEstado(nuevoEstado);
        pedido.setFormaPago(nuevaFormaPago);
    }

    // 4. ELIMINAR / BAJA LÓGICA (HU-PED-04)
    public void eliminarPedido(Long id) throws EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(id);
        pedido.setEliminado(true);
        // Propagamos la eliminación a los detalles para ser prolijos:
        for (DetallePedido dp : pedido.getDetallePedidos()) {
            dp.setEliminado(true);
        }
    }
}
