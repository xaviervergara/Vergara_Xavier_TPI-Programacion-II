/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.util.ArrayList;
/**
 *
 * @author Xavier
 */
public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private ArrayList<Producto> productos; //agregacion 1:n
    
    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>(); // agregacion 1:n
    }
    
    //getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    //setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void agregarProducto(Producto p){
        this.productos.add(p);
    };

    @Override
    public String toString() {
        return "Categoria{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", productos=" + productos + '}';
    }
    
}



