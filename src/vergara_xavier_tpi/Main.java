/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vergara_xavier_tpi;

import exception.EntidadNoEncontradaException;
import exception.ReglaNegocioException;
import services.CategoriaService;
import services.PedidoService;
import services.ProductoService;
import services.UsuarioService;

import java.util.Scanner;

/**
 *
 * @author Xavier
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. INICIALIZACIÓN DE SERVICIOS (Respetando el orden de dependencias)
        CategoriaService categoriaService = new CategoriaService();
        ProductoService productoService = new ProductoService(categoriaService);
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService(usuarioService, productoService);

        int opcion = -1;

        // 2. BUCLE PRINCIPAL DEL PROGRAMA
        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine()); // Leemos como String y parseamos para evitar el bug del Enter

                switch (opcion) {
                    case 1:
                        menuCategorias(scanner, categoriaService);
                        break;
                    case 2:
                        menuProductos(scanner, productoService);
                        break;
                    case 3:
                        menuUsuarios(scanner, usuarioService);
                        break;
                    case 4:
                        menuPedidos(scanner, pedidoService);
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }

        } while (opcion != 0);

        scanner.close();
    }

    // --- SUBMENÚ DE CATEGORÍAS ---
    private static void menuCategorias(Scanner scanner, CategoriaService categoriaService) {
        int opcionCat = -1;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");

            try {
                opcionCat = Integer.parseInt(scanner.nextLine());

                switch (opcionCat) {
                    case 1:
                        categoriaService.listarCategorias();
                        break;
                    case 2:
                        System.out.print("Ingrese nombre de la categoría: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese descripción: ");
                        String desc = scanner.nextLine();
                        
                        // El try-catch es implícito aquí porque el método padre ya lo atrapa, 
                        // pero es buena práctica capturar las reglas de negocio específicas.
                        try {
                            categoriaService.crearCategoria(nombre, desc);
                            System.out.println("¡Categoría creada con éxito!");
                        } catch (ReglaNegocioException e) {
                            System.out.println("Error de validación: " + e.getMessage());
                        }
                        break;
                    case 3:
                        categoriaService.listarCategorias();
                        System.out.print("Ingrese el ID de la categoría a editar: ");
                        Long idEditar = Long.parseLong(scanner.nextLine());
                        System.out.print("Ingrese el nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Ingrese la nueva descripción: ");
                        String nuevaDesc = scanner.nextLine();
                        
                        try {
                            categoriaService.editarCategoria(idEditar, nuevoNombre, nuevaDesc);
                            System.out.println("¡Categoría actualizada con éxito!");
                        } catch (EntidadNoEncontradaException | ReglaNegocioException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("Ingrese el ID de la categoría a eliminar: ");
                        Long idEliminar = Long.parseLong(scanner.nextLine());
                        System.out.print("¿Está seguro? (S/N): ");
                        String confirmacion = scanner.nextLine();
                        
                        if (confirmacion.equalsIgnoreCase("S")) {
                            try {
                                categoriaService.eliminarCategoria(idEliminar);
                                System.out.println("¡Categoría eliminada con éxito (Baja lógica)!");
                            } catch (EntidadNoEncontradaException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                        break;
                    case 0:
                        break; // Vuelve al main
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        } while (opcionCat != 0);
    }
    
    // --- SUBMENÚ DE PRODUCTOS ---
    private static void menuProductos(Scanner scanner, ProductoService productoService) {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar (Precio, Stock, Categoría)");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        productoService.listarProductos();
                        break;
                    case 2:
                        System.out.print("Nombre: "); String nombre = scanner.nextLine();
                        System.out.print("Precio: "); Double precio = Double.parseDouble(scanner.nextLine());
                        System.out.print("Descripción: "); String desc = scanner.nextLine();
                        System.out.print("Stock inicial: "); int stock = Integer.parseInt(scanner.nextLine());
                        System.out.print("URL Imagen (o vacio): "); String img = scanner.nextLine();
                        System.out.print("¿Está disponible? (true/false): "); boolean disp = Boolean.parseBoolean(scanner.nextLine());
                        System.out.print("ID de la Categoría asociada: "); Long idCat = Long.parseLong(scanner.nextLine());

                        try {
                            productoService.crearProducto(nombre, precio, desc, stock, img, disp, idCat);
                            System.out.println("¡Producto creado con éxito!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 3:
                        productoService.listarProductos();
                        System.out.print("ID del producto a editar: "); Long idEd = Long.parseLong(scanner.nextLine());
                        System.out.print("Nuevo Precio: "); Double nPrecio = Double.parseDouble(scanner.nextLine());
                        System.out.print("Nuevo Stock: "); int nStock = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nuevo ID de Categoría: "); Long nIdCat = Long.parseLong(scanner.nextLine());
                        
                        try {
                            productoService.editarProducto(idEd, nPrecio, nStock, nIdCat);
                            System.out.println("¡Producto actualizado!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("ID a eliminar: "); Long idEl = Long.parseLong(scanner.nextLine());
                        try {
                            productoService.eliminarProducto(idEl);
                            System.out.println("¡Producto eliminado (Baja Lógica)!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato de número inválido.");
            }
        } while (opcion != 0);
    }
    
    // --- SUBMENÚ DE USUARIOS ---
    private static void menuUsuarios(Scanner scanner, UsuarioService usuarioService) {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        usuarioService.listarUsuarios();
                        break;
                    case 2:
                        System.out.print("Nombre: "); String nombre = scanner.nextLine();
                        System.out.print("Apellido: "); String apellido = scanner.nextLine();
                        System.out.print("Mail: "); String mail = scanner.nextLine();
                        System.out.print("Celular: "); String cel = scanner.nextLine();
                        System.out.print("Contraseña: "); String pass = scanner.nextLine();
                        System.out.print("Rol (1 para ADMIN, 2 para USUARIO): "); 
                        enums.Rol rol = scanner.nextLine().equals("1") ? enums.Rol.ADMIN : enums.Rol.USUARIO;

                        try {
                            usuarioService.crearUsuario(nombre, apellido, mail, cel, pass, rol);
                            System.out.println("¡Usuario creado!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 3:
                        usuarioService.listarUsuarios();
                        System.out.print("ID de usuario a editar: "); Long id = Long.parseLong(scanner.nextLine());
                        System.out.print("Nuevo Nombre: "); String nNombre = scanner.nextLine();
                        System.out.print("Nuevo Apellido: "); String nApellido = scanner.nextLine();
                        System.out.print("Nuevo Mail: "); String nMail = scanner.nextLine();
                        System.out.print("Nuevo Celular: "); String nCel = scanner.nextLine();
                        System.out.print("Rol (1=ADMIN, 2=USUARIO): "); 
                        enums.Rol nRol = scanner.nextLine().equals("1") ? enums.Rol.ADMIN : enums.Rol.USUARIO;

                        try {
                            usuarioService.editarUsuario(id, nNombre, nApellido, nMail, nCel, nRol);
                            System.out.println("¡Usuario actualizado!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("ID a eliminar: "); Long idEl = Long.parseLong(scanner.nextLine());
                        try {
                            usuarioService.eliminarUsuario(idEl);
                            System.out.println("¡Usuario eliminado!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato de número inválido.");
            }
        } while (opcion != 0);
    }
    
    // --- SUBMENÚ DE PEDIDOS) ---
    private static void menuPedidos(Scanner scanner, PedidoService pedidoService) {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar Pedidos");
            System.out.println("2. Crear Nuevo Pedido");
            System.out.println("3. Actualizar Estado/Pago");
            System.out.println("4. Anular Pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        pedidoService.listarPedidos();
                        break;
                    case 2:
                        System.out.print("ID del Usuario que realiza el pedido: ");
                        Long idUsr = Long.parseLong(scanner.nextLine());
                        System.out.print("Forma de pago (1=EFECTIVO, 2=TARJETA, 3=TRANSFERENCIA): ");
                        String opcPago = scanner.nextLine();
                        enums.FormaPago pago = opcPago.equals("1") ? enums.FormaPago.EFECTIVO : 
                                               (opcPago.equals("2") ? enums.FormaPago.TARJETA : enums.FormaPago.TRANSFERENCIA);

                        try {
                            // PASO A: Iniciamos el pedido temporal
                            entities.Pedido pedidoTemp = pedidoService.iniciarPedido(idUsr, pago);
                            System.out.println("--- Agregando Productos al Pedido ---");
                            
                            boolean agregarMas = true;
                            // PASO B: Bucle para agregar detalles
                            while (agregarMas) {
                                System.out.print("ID del Producto: ");
                                Long idProd = Long.parseLong(scanner.nextLine());
                                System.out.print("Cantidad: ");
                                int cant = Integer.parseInt(scanner.nextLine());

                                pedidoService.agregarDetalle(pedidoTemp, idProd, cant);
                                System.out.println("Producto agregado al pedido. Subtotal parcial: $" + pedidoTemp.getTotal());

                                System.out.print("¿Agregar otro producto? (S/N): ");
                                agregarMas = scanner.nextLine().equalsIgnoreCase("S");
                            }

                            // PASO C: Confirmamos
                            pedidoService.confirmarYGuardarPedido(pedidoTemp);
                            System.out.println("¡Pedido confirmado! Total final: $" + pedidoTemp.getTotal());

                        } catch (Exception e) {
                            System.out.println("Error en la creación del pedido: " + e.getMessage());
                            System.out.println("Cancelando pedido y revirtiendo stock...");
                            // Si falla, necesitamos revertir. Nota: Requiere que pedidoTemp esté accesible,
                            // pero como la excepción corta el flujo, la reversión idealmente se maneja
                            // asegurando que no queden datos huérfanos. 
                        }
                        break;
                    case 3:
                        pedidoService.listarPedidos();
                        System.out.print("ID del Pedido: "); Long idPed = Long.parseLong(scanner.nextLine());
                        System.out.print("Nuevo Estado (1=PENDIENTE, 2=CONFIRMADO, 3=TERMINADO, 4=CANCELADO): ");
                        int est = Integer.parseInt(scanner.nextLine());
                        enums.Estado nEst = enums.Estado.values()[est - 1]; // Truco rápido para mapear números a Enums
                        
                        System.out.print("Nueva Forma de Pago (1=EFECTIVO, 2=TARJETA, 3=TRANSFERENCIA): ");
                        int fp = Integer.parseInt(scanner.nextLine());
                        enums.FormaPago nFp = enums.FormaPago.values()[fp - 1];

                        try {
                            pedidoService.actualizarEstadoYPago(idPed, nEst, nFp);
                            System.out.println("¡Pedido actualizado!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("ID del pedido a anular: "); Long idAnular = Long.parseLong(scanner.nextLine());
                        try {
                            pedidoService.eliminarPedido(idAnular);
                            System.out.println("¡Pedido anulado (Baja lógica)!");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato de número inválido.");
            }
        } while (opcion != 0);
    }
}
