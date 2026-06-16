/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Usuario;
import enums.Rol;
import exception.EntidadNoEncontradaException;
import exception.ReglaNegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xavier
 */

public class UsuarioService {

    private List<Usuario> usuarios;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    // 1. LISTAR (HU-USR-01)
    public void listarUsuarios() {
        boolean hayUsuarios = false;
        System.out.println("\n--- LISTADO DE USUARIOS ---");
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                System.out.println("ID: " + u.getId() + " | Nombre: " + u.getNombre() + " " + u.getApellido() + 
                                   " | Mail: " + u.getMail() + " | Rol: " + u.getRol());
                hayUsuarios = true;
            }
        }
        if (!hayUsuarios) {
            System.out.println("No hay usuarios cargados o activos.");
        }
    }

    // MÉTODO AUXILIAR: Buscar Usuario por ID
    public Usuario buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró un usuario activo con el ID: " + id);
    }

    // 2. CREAR (HU-USR-02)
    public Usuario crearUsuario(String nombre, String apellido, String mail, String celular, 
                                String contrasenia, Rol rol) throws ReglaNegocioException {
        
        if (mail == null || mail.trim().isEmpty()) {
            throw new ReglaNegocioException("El mail del usuario no puede estar vacío.");
        }

        // Regla de Negocio: Validar unicidad del mail
        for (Usuario u : usuarios) {
            if (u.getMail().equalsIgnoreCase(mail) && !u.isEliminado()) {
                throw new ReglaNegocioException("Ya existe un usuario registrado con el mail: " + mail);
            }
        }

        Usuario nuevoUsuario = new Usuario(nombre, apellido, mail, celular, contrasenia, rol);
        usuarios.add(nuevoUsuario);
        return nuevoUsuario;
    }

    // 3. EDITAR (HU-USR-03)
    public void editarUsuario(Long id, String nuevoNombre, String nuevoApellido, 
                              String nuevoMail, String nuevoCelular, Rol nuevoRol) 
                              throws EntidadNoEncontradaException, ReglaNegocioException {
        
        Usuario usuario = buscarPorId(id);

        // Si el operador decide cambiar el mail, tenemos que validar que el nuevo no esté ocupado
        if (!usuario.getMail().equalsIgnoreCase(nuevoMail)) {
            for (Usuario u : usuarios) {
                if (u.getMail().equalsIgnoreCase(nuevoMail) && !u.isEliminado()) {
                    throw new ReglaNegocioException("El mail " + nuevoMail + " ya está en uso por otro usuario.");
                }
            }
        }

        usuario.setNombre(nuevoNombre);
        usuario.setApellido(nuevoApellido);
        usuario.setMail(nuevoMail);
        usuario.setCelular(nuevoCelular);
        usuario.setRol(nuevoRol);
    }

    // 4. ELIMINAR / BAJA LÓGICA (HU-USR-04)
    public void eliminarUsuario(Long id) throws EntidadNoEncontradaException {
        Usuario usuario = buscarPorId(id);
        usuario.setEliminado(true);
    }
}