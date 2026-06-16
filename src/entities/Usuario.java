/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.Rol;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xavier
 */
public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private List<Pedido> pedidos = new ArrayList<>(); //asociacion 1:n

    public Usuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }
    
    public void agregarPedido(Pedido p){
        this.pedidos.add(p);
    };

    //getters
    
    public String getMail() {
        return mail;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Rol getRol() {
        return rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    //setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
    
    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail + ", celular=" + celular + ", contrase\u00f1a=" + contrasenia + ", rol=" + rol + '}';
    }

    
}
