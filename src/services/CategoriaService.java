/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Categoria;
import exception.EntidadNoEncontradaException;
import exception.ReglaNegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xavier
 */
public class CategoriaService {
    
    // bd en memoriaA
    private List<Categoria> categorias;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
    }

    // --- MÉTODOS CRUD ---

    // 1. LISTAR (HU-CAT-01)
    public void listarCategorias() {
        boolean hayCategorias = false;
        System.out.println("\n--- LISTADO DE CATEGORÍAS ---");
        for (Categoria c : categorias) {
            if (!c.isEliminado()) { // Solo mostramos las no eliminadas
                System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre() + " | Descripción: " + c.getDescripcion());
                hayCategorias = true;
            }
        }
        if (!hayCategorias) {
            System.out.println("No hay categorías cargadas o activas.");
        }
    }

    // MÉTDO AUXILIAR INTERNO: Buscar por ID
    public Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró ninguna categoría activa con el ID: " + id);
    }

    // 2. CREAR (HU-CAT-02)
    public Categoria crearCategoria(String nombre, String descripcion) throws ReglaNegocioException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaNegocioException("El nombre de la categoría no puede estar vacío.");
        }
        
        // Validar unicidad del nombre
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre) && !c.isEliminado()) {
                throw new ReglaNegocioException("Ya existe una categoría activa con el nombre: " + nombre);
            }
        }

        Categoria nuevaCategoria = new Categoria(nombre, descripcion);
        categorias.add(nuevaCategoria);
        return nuevaCategoria;
    }

    // 3. EDITAR (HU-CAT-03)
    public void editarCategoria(Long id, String nuevoNombre, String nuevaDescripcion) throws EntidadNoEncontradaException, ReglaNegocioException {
        Categoria categoria = buscarPorId(id); // Si no existe, lanza la excepción y corta la ejecución aquí

        // Si el nombre cambia, validamos que el nuevo no esté repetido
        if (!categoria.getNombre().equalsIgnoreCase(nuevoNombre)) {
            for (Categoria c : categorias) {
                if (c.getNombre().equalsIgnoreCase(nuevoNombre) && !c.isEliminado()) {
                    throw new ReglaNegocioException("Ya existe otra categoría con el nombre: " + nuevoNombre);
                }
            }
        }

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(nuevaDescripcion);
    }

    // 4. ELIMINAR / BAJA LÓGICA (HU-CAT-04)
    public void eliminarCategoria(Long id) throws EntidadNoEncontradaException {
        Categoria categoria = buscarPorId(id);
        categoria.setEliminado(true); // Soft delete
    }
}
