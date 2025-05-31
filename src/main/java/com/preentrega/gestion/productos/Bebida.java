package com.preentrega.gestion.productos;

public class Bebida extends Producto {
    private double volumenLitros;

    public Bebida(String nombre, double precio, int stock, double volumenLitros) {
        super(nombre, precio, stock);
        this.volumenLitros = volumenLitros;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Volumen: %.2fL", volumenLitros);
    }
}
