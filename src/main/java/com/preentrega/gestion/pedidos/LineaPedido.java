package com.preentrega.gestion.pedidos;


import com.preentrega.gestion.productos.Producto;

public class LineaPedido {
    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return String.format("%s | Cantidad: %d | Subtotal: %.2f", producto.toString(), cantidad, getSubtotal());
    }
}
