/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categoria;
import entities.Producto;
import exception.EntidadNoEncontradaException;
import exception.ReglaNegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xavier
 */
public class ProductoService {

    private List<Producto> productos;
    // IMPORTANTE: Inyectamos el servicio de categorías para poder buscarlas
    private CategoriaService categoriaService; 

    public ProductoService(CategoriaService categoriaService) {
        this.productos = new ArrayList<>();
        this.categoriaService = categoriaService;
    }

    // 1. LISTAR (HU-PROD-01)
    public void listarProductos() {
        boolean hayProductos = false;
        System.out.println("\n--- LISTADO DE PRODUCTOS ---");
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + 
                                   " | Precio: $" + p.getPrecio() + " | Stock: " + p.getStock() + 
                                   " | Categoría: " + p.getCategoria().getNombre());
                hayProductos = true;
            }
        }
        if (!hayProductos) {
            System.out.println("No hay productos cargados o activos.");
        }
    }

    // MÉTODO AUXILIAR: Buscar Producto por ID
    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró un producto activo con el ID: " + id);
    }

    // 2. CREAR (HU-PROD-02)
    public Producto crearProducto(String nombre, Double precio, String descripcion, int stock, 
                                  String imagen, boolean disponible, Long idCategoria) 
                                  throws ReglaNegocioException, EntidadNoEncontradaException {
        
        // Reglas de negocio estrictas
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre del producto no puede estar vacío.");
        }
        if (precio < 0) {
            throw new ReglaNegocioException("El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new ReglaNegocioException("El stock no puede ser negativo.");
        }

        // Buscamos la categoría usando el servicio de categorías. 
        // Si no existe, el método buscarPorId lanzará la excepción automáticamente.
        Categoria categoriaAsociada = categoriaService.buscarPorId(idCategoria);

        Producto nuevoProducto = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoriaAsociada);
        
        // Relación bidireccional: agregamos el producto a la lista interna de la categoría
        categoriaAsociada.agregarProducto(nuevoProducto);
        
        productos.add(nuevoProducto);
        return nuevoProducto;
    }

    // 3. EDITAR (HU-PROD-03)
    public void editarProducto(Long id, Double nuevoPrecio, int nuevoStock, Long idNuevaCategoria) 
                               throws EntidadNoEncontradaException, ReglaNegocioException {
        
        if (nuevoPrecio < 0 || nuevoStock < 0) {
            throw new ReglaNegocioException("El precio y el stock no pueden ser negativos.");
        }

        Producto producto = buscarPorId(id);
        Categoria nuevaCategoria = categoriaService.buscarPorId(idNuevaCategoria);

        producto.setPrecio(nuevoPrecio);
        producto.setStock(nuevoStock);
        producto.setCategoria(nuevaCategoria);
    }

    // 4. ELIMINAR / BAJA LÓGICA (HU-PROD-04)
    public void eliminarProducto(Long id) throws EntidadNoEncontradaException {
        Producto producto = buscarPorId(id);
        producto.setEliminado(true);
    }
}
