package com.preentrega.gestion.productos;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Producto {
    private static int contador = 1;
    protected int id;
    protected String nombre;
    protected double precio;
    protected int stock;

    private static List<Integer> idsDisponibles = new ArrayList<>();

    public static void liberarId(int id) {
        idsDisponibles.add(id);
        Collections.sort(idsDisponibles);
    }

    public Producto(String nombre, double precio, int stock) {
        if (!idsDisponibles.isEmpty()) {
            this.id = idsDisponibles.remove(0);
        } else {
            this.id = contador++;
        }
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Precio: %.2f | Stock: %d", id, nombre, precio, stock);
    }
}