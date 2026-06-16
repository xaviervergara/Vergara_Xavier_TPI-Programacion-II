/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;


/**
 *
 * @author Xavier
 */
public class DetallePedido extends Base {
    private int cantidad;
    private double subtotal;
    private Producto producto;

    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }

    public Producto getProducto() {
        return producto;
    }


    public Double getSubtotal() {
        return subtotal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    //metodo privado
    private Double calcularSubtotal(){
        return cantidad * producto.getPrecio();
    }

    @Override
    public String toString() {
        return "DetallePedido{" + "cantidad=" + cantidad + ", subtotal=" + subtotal + '}';
    }
   
}
