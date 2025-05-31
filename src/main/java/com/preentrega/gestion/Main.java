package com.preentrega.gestion;

import com.preentrega.gestion.productos.*;
import com.preentrega.gestion.pedidos.*;
import com.preentrega.gestion.excepciones.*;

import java.util.*;

public class Main {
    static ProductoService productoService = new ProductoService();
    static ArrayList<Pedido> pedidos = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> listarProductos();
                    case 3 -> buscarActualizarProducto();
                    case 4 -> eliminarProducto();
                    case 5 -> crearPedido();
                    case 6 -> listarPedidos();
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                opcion = 0;
            }
        } while (opcion != 7);
    }

    static void mostrarMenu() {
        System.out.println("\n--- MENÚ ---");
        System.out.println("1. Agregar Producto");
        System.out.println("2. Listar Productos");
        System.out.println("3. Buscar/Actualizar Producto");
        System.out.println("4. Eliminar Producto");
        System.out.println("5. Crear Pedido");
        System.out.println("6. Listar Pedidos");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    static void agregarProducto() {
        System.out.print("¿El producto es una bebida? (s/n): ");
        String esBebida = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        double precio;
        while (true) {
            System.out.print("Precio: ");
            try {
                precio = Double.parseDouble(scanner.nextLine());
                if (precio < 0) {
                    System.out.println("El precio no puede ser negativo.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Formato inválido. Ingrese un número válido para el precio.");
            }
        }

        int stock;
        while (true) {
            System.out.print("Stock: ");
            try {
                stock = Integer.parseInt(scanner.nextLine());
                if (stock < 0) {
                    System.out.println("El stock no puede ser negativo.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Formato inválido. Ingrese un número válido para el stock.");
            }
        }

        if (esBebida.equalsIgnoreCase("s")) {
            double volumen;
            while (true) {
                System.out.print("Volumen en litros: ");
                try {
                    volumen = Double.parseDouble(scanner.nextLine());
                    if (volumen <= 0) {
                        System.out.println("El volumen debe ser mayor que cero.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Formato inválido. Ingrese un número válido para el volumen.");
                }
            }
            productoService.agregarProducto(new Bebida(nombre, precio, stock, volumen));
        } else {
            productoService.agregarProducto(new Producto(nombre, precio, stock));
        }

        System.out.println("Producto agregado correctamente.");
    }


    static void listarProductos() {
        ArrayList<Producto> productos = productoService.listarProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
            return;
        }
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    static void buscarActualizarProducto() {
        System.out.print("Buscar producto por (1) ID o (2) nombre parcial? ");
        String opcion = scanner.nextLine();

        Producto producto = null;
        if (opcion.equals("1")) {
            System.out.print("Ingrese ID del producto: ");
            int id = Integer.parseInt(scanner.nextLine());
            producto = productoService.buscarPorId(id);
            if (producto == null) {
                System.out.println("No se encontró producto con ese ID.");
                return;
            }
        } else if (opcion.equals("2")) {
            System.out.print("Ingrese parte del nombre del producto: ");
            String nombre = scanner.nextLine();

            ArrayList<Producto> resultados = productoService.buscarPorNombre(nombre);
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron productos con ese nombre.");
                return;
            }

            System.out.println("Productos encontrados:");
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println((i + 1) + ". " + resultados.get(i));
            }

            System.out.print("Seleccione el número del producto a actualizar: ");
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;

            if (seleccion >= 0 && seleccion < resultados.size()) {
                producto = resultados.get(seleccion);
            } else {
                System.out.println("Selección inválida.");
                return;
            }
        } else {
            System.out.println("Opción inválida.");
            return;
        }

        // Actualizar producto
        System.out.print("Actualizar precio? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Nuevo precio: ");
            producto.setPrecio(Double.parseDouble(scanner.nextLine()));
        }

        System.out.print("Actualizar stock? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Nuevo stock: ");
            producto.setStock(Integer.parseInt(scanner.nextLine()));
        }

        System.out.println("Producto actualizado:\n" + producto);
    }

    static void eliminarProducto() {
        System.out.print("Eliminar producto por (1) ID o (2) nombre parcial? ");
        String opcion = scanner.nextLine();

        if (opcion.equals("1")) {
            System.out.print("Ingrese ID de producto a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean eliminado = productoService.eliminarProducto(id);
            System.out.println(eliminado ? "Producto eliminado." : "No se encontró el producto.");
        } else if (opcion.equals("2")) {
            System.out.print("Ingrese parte del nombre del producto a eliminar: ");
            String nombre = scanner.nextLine();
            ArrayList<Producto> resultados = productoService.buscarPorNombre(nombre);

            if (resultados.isEmpty()) {
                System.out.println("No se encontraron productos con ese nombre.");
                return;
            }

            System.out.println("Productos encontrados:");
            for (int i = 0; i < resultados.size(); i++) {
                System.out.println((i + 1) + ". " + resultados.get(i));
            }

            System.out.print("Seleccione el número del producto a eliminar: ");
            int seleccion = Integer.parseInt(scanner.nextLine()) - 1;

            if (seleccion >= 0 && seleccion < resultados.size()) {
                Producto producto = resultados.get(seleccion);
                boolean eliminado = productoService.eliminarProducto(producto.getId());
                System.out.println(eliminado ? "Producto eliminado." : "Error al eliminar producto.");
            } else {
                System.out.println("Selección inválida.");
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    static void crearPedido() {
        Pedido pedido = new Pedido();

        while (true) {
            System.out.print("Buscar producto por (1) ID, (2) nombre parcial o (-1) para finalizar: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("-1")) break;

            Producto producto = null;

            if (opcion.equals("1")) {
                System.out.print("Ingrese ID del producto: ");
                int id = Integer.parseInt(scanner.nextLine());
                producto = productoService.buscarPorId(id);
                if (producto == null) {
                    System.out.println("Producto no encontrado.");
                    continue;
                }
            } else if (opcion.equals("2")) {
                System.out.print("Ingrese parte del nombre del producto: ");
                String nombre = scanner.nextLine();

                ArrayList<Producto> resultados = productoService.buscarPorNombre(nombre);

                if (resultados.isEmpty()) {
                    System.out.println("No se encontraron productos con ese nombre.");
                    continue;
                }

                System.out.println("Productos encontrados:");
                for (int i = 0; i < resultados.size(); i++) {
                    System.out.println((i + 1) + ". " + resultados.get(i));
                }

                System.out.print("Seleccione el número del producto a agregar: ");
                int seleccion = Integer.parseInt(scanner.nextLine()) - 1;

                if (seleccion >= 0 && seleccion < resultados.size()) {
                    producto = resultados.get(seleccion);
                } else {
                    System.out.println("Selección inválida.");
                    continue;
                }
            } else {
                System.out.println("Opción inválida.");
                continue;
            }

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            try {
                if (cantidad > producto.getStock()) {
                    throw new StockInsuficienteException("Stock insuficiente para " + producto.getNombre());
                }
                pedido.agregarLinea(new LineaPedido(producto, cantidad));
                producto.setStock(producto.getStock() - cantidad);
            } catch (StockInsuficienteException e) {
                System.out.println(e.getMessage());
            }
        }

        if (pedido != null && !pedido.listarLineas().isEmpty()) {
            pedidos.add(pedido);
            System.out.println("Pedido creado:\n" + pedido);
        } else {
            System.out.println("No se agregaron productos al pedido.");
        }
    }

    static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos para mostrar.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }
}
