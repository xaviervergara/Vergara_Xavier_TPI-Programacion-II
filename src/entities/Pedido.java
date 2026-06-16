/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import enums.Estado;
import enums.FormaPago;
import java.time.LocalDate;
import interfaces.Calculable;
import java.util.ArrayList;

/**
 *
 * @author Xavier
 */
public class Pedido extends Base implements Calculable{
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private ArrayList<DetallePedido> detallePedidos;


    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario) {
        super();
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detallePedidos = new ArrayList<>();
    }
    //getters
    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    //Setters

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    
    //Metodo de la interfaz
    public void calcularTotal(){
        double acu = 0;
        for (DetallePedido detallePedido : detallePedidos) {
            acu = acu + detallePedido.getSubtotal();
        }
        this.total = acu;
    };

    public ArrayList<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void addDetallePedido(int cantidad, Producto p){
        detallePedidos.add(new DetallePedido(cantidad, p));
        
        this.calcularTotal();
    };
    
    public DetallePedido findDetallePedidoByProducto(Producto p) {
        for (DetallePedido detallePedido : detallePedidos) {
            if(detallePedido.getProducto().getId().equals(p.getId()))
                return detallePedido;
        }
        return null;
    }
    
    public void deleteDetallePedidoByProducto(Producto p){
        DetallePedido detalleEncontrado = findDetallePedidoByProducto(p);
        
        if(detalleEncontrado != null){
            detallePedidos.remove(detalleEncontrado);
            this.calcularTotal();
        }
        
    };

    @Override
    public String toString() {
        return "Pedido{" + "fecha=" + fecha + ", estado=" + estado + ", total=" + total + ", formaPago=" + formaPago + ", detallePedidos=" + detallePedidos + ", usuario=" + usuario + '}';
    }
  
}
