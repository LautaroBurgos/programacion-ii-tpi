package ar.edu.tfi.main;

import ar.edu.tfi.entities.*;
import ar.edu.tfi.service.PedidoService;
import ar.edu.tfi.service.EnvioService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppMenu {
    private final PedidoService pedidoService = new PedidoService();
    private final EnvioService envioService = new EnvioService();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- MENU Pedido->Envio ---");
            System.out.println("1) Crear pedido con envio");
            System.out.println("2) Listar pedidos");
            System.out.println("3) Ver pedido por ID");
            System.out.println("4) Actualizar pedido");
            System.out.println("5) Eliminar pedido (baja lógica)");
            System.out.println("6) Listar envíos");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String opt = sc.nextLine().trim();
            try {
                switch (opt) {
                    case "1": crearPedidoFlow(); break;
                    case "2": listarPedidos(); break;
                    case "3": verPedido(); break;
                    case "4": actualizarPedido(); break;
                    case "5": eliminarPedido(); break;
                    case "6": listarEnvios(); break;
                    case "0": running = false; break;
                    default: System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Saliendo...");
    }

    private void crearPedidoFlow() throws Exception {
        System.out.println("-- Crear pedido con envio --");
        Pedido p = new Pedido();
        System.out.print("Número (ej: P-00001): "); p.setNumero(sc.nextLine().trim());
        System.out.print("Fecha (YYYY-MM-DD): "); p.setFecha(LocalDate.parse(sc.nextLine().trim()));
        System.out.print("Cliente nombre: "); p.setClienteNombre(sc.nextLine().trim());
        System.out.print("Total (ej: 1500.50): "); p.setTotal(new BigDecimal(sc.nextLine().trim()));
        System.out.print("Estado (NUEVO/FACTURADO/ENVIADO): "); p.setEstado(EstadoPedido.valueOf(sc.nextLine().trim().toUpperCase()));

        Envio e = new Envio();
        System.out.print("Tracking: "); e.setTracking(sc.nextLine().trim());
        System.out.print("Empresa (ANDREANI/OCA/CORREO_ARG): "); e.setEmpresa(EmpresaEnvio.valueOf(sc.nextLine().trim().toUpperCase()));
        System.out.print("Tipo (ESTANDAR/EXPRES): "); e.setTipo(TipoEnvio.valueOf(sc.nextLine().trim().toUpperCase()));
        System.out.print("Costo: "); e.setCosto(new BigDecimal(sc.nextLine().trim()));
        System.out.print("Fecha despacho (YYYY-MM-DD) o vacío: "); String fd = sc.nextLine().trim(); if (!fd.isEmpty()) e.setFechaDespacho(LocalDate.parse(fd));
        System.out.print("Fecha estimada (YYYY-MM-DD) o vacío: "); String fe = sc.nextLine().trim(); if (!fe.isEmpty()) e.setFechaEstimada(LocalDate.parse(fe));
        System.out.print("Estado envio (EN_PREPARACION/EN_TRANSITO/ENTREGADO): "); e.setEstado(EstadoEnvio.valueOf(sc.nextLine().trim().toUpperCase()));

        p.setEnvio(e);
        pedidoService.crearPedidoConEnvio(p);
        System.out.println("Pedido creado con ID: " + p.getId());
    }

    private void listarPedidos() throws Exception {
        List<Pedido> lista = pedidoService.listarPedidos();
        System.out.println("-- Pedidos --");
        lista.forEach(System.out::println);
    }

    private void verPedido() throws Exception {
        System.out.print("ID pedido: "); long id = Long.parseLong(sc.nextLine().trim());
        Pedido p = pedidoService.getById(id);
        if (p==null) System.out.println("Pedido no encontrado"); else System.out.println(p);
        if (p!=null && p.getEnvio()!=null) {
            System.out.println("Detalle envio: " + p.getEnvio());
        }
    }

    private void actualizarPedido() throws Exception {
        System.out.print("ID pedido a actualizar: "); long id = Long.parseLong(sc.nextLine().trim());
        Pedido p = pedidoService.getById(id);
        if (p==null) { System.out.println("Pedido no encontrado"); return; }
        System.out.println("Dejar vacío para mantener valor actual.");
        System.out.print("Número ["+p.getNumero()+"]: "); String num = sc.nextLine().trim(); if (!num.isEmpty()) p.setNumero(num);
        System.out.print("Fecha ["+p.getFecha()+"] (YYYY-MM-DD): "); String f = sc.nextLine().trim(); if (!f.isEmpty()) p.setFecha(LocalDate.parse(f));
        System.out.print("Cliente nombre ["+p.getClienteNombre()+"]: "); String cli = sc.nextLine().trim(); if (!cli.isEmpty()) p.setClienteNombre(cli);
        System.out.print("Total ["+p.getTotal()+"]: "); String tot = sc.nextLine().trim(); if (!tot.isEmpty()) p.setTotal(new BigDecimal(tot));
        System.out.print("Estado ["+p.getEstado()+"]: "); String est = sc.nextLine().trim(); if (!est.isEmpty()) p.setEstado(EstadoPedido.valueOf(est.toUpperCase()));

        Envio e = p.getEnvio();
        if (e==null) e = new Envio();
        System.out.print("Tracking ["+(e.getTracking()!=null?e.getTracking():"")+"]: "); String tr = sc.nextLine().trim(); if (!tr.isEmpty()) e.setTracking(tr);
        System.out.print("Empresa ["+(e.getEmpresa()!=null?e.getEmpresa():"")+"]: "); String emp = sc.nextLine().trim(); if (!emp.isEmpty()) e.setEmpresa(EmpresaEnvio.valueOf(emp.toUpperCase()));
        System.out.print("Tipo ["+(e.getTipo()!=null?e.getTipo():"")+"]: "); String tip = sc.nextLine().trim(); if (!tip.isEmpty()) e.setTipo(TipoEnvio.valueOf(tip.toUpperCase()));
        System.out.print("Costo ["+(e.getCosto()!=null?e.getCosto():"")+"]: "); String cos = sc.nextLine().trim(); if (!cos.isEmpty()) e.setCosto(new BigDecimal(cos));
        System.out.print("Fecha despacho ["+(e.getFechaDespacho()!=null?e.getFechaDespacho():"")+"]: "); String fd = sc.nextLine().trim(); if (!fd.isEmpty()) e.setFechaDespacho(LocalDate.parse(fd));
        System.out.print("Fecha estimada ["+(e.getFechaEstimada()!=null?e.getFechaEstimada():"")+"]: "); String fe = sc.nextLine().trim(); if (!fe.isEmpty()) e.setFechaEstimada(LocalDate.parse(fe));
        System.out.print("Estado envio ["+(e.getEstado()!=null?e.getEstado():"")+"]: "); String eest = sc.nextLine().trim(); if (!eest.isEmpty()) e.setEstado(EstadoEnvio.valueOf(eest.toUpperCase()));

        p.setEnvio(e);
        pedidoService.actualizarPedidoConEnvio(p);
        System.out.println("Pedido actualizado.");
    }

    private void eliminarPedido() throws Exception {
        System.out.print("ID a eliminar lógicamente: ");
        long id = Long.parseLong(sc.nextLine().trim());
        pedidoService.eliminarPedidoLogico(id);
        System.out.println("Pedido marcado como eliminado.");
    }

    private void listarEnvios() throws Exception {
        List<Envio> lista = envioService.listarEnvios();
        System.out.println("-- Envios --");
        lista.forEach(System.out::println);
    }
}
