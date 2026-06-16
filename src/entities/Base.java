/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.time.LocalDateTime;

/**
 *
 * @author Xavier
 */
public abstract class Base {
    
    private static Long contadorId = 0L;
    
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    public Base() {
        contadorId++;
        this.id = contadorId;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now(); //guardamos fecha y hra exacta al momento de instanciar;
    }

    
    //GETTERS
    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //SETTERS
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public abstract String toString();
}
