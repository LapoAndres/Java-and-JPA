package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.CategoriaDAO;
import com.latam.alura.tienda.dao.ClienteDAO;
import com.latam.alura.tienda.dao.PedidoDAO;
import com.latam.alura.tienda.dao.ProductoDAO;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Cliente;
import com.latam.alura.tienda.modelo.ItemsPedido;
import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;
import com.latam.alura.tienda.vo.RelatorioDeVenta;

public class RegistroDeProducto {

	public static void main(String[] args) {
		registrarProducto();
		EntityManager em = JPAUtils.getEntityManager();	
		ProductoDAO productoDao= new ProductoDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		PedidoDAO pedidoDao= new PedidoDAO(em);
		Producto producto = productoDao.consultaPorId(1l);
		Producto producto2 = productoDao.consultaPorId(2l);
		Producto producto3 = productoDao.consultaPorId(3l);
		Producto producto4 = productoDao.consultaPorId(4l);
		
		Cliente cliente = new Cliente("Andres Panchi", "1754099686");
		Pedido pedido = new Pedido(cliente);
		pedido.agregarItems(new ItemsPedido(5,producto2,pedido));
		pedido.agregarItems(new ItemsPedido(1,producto4,pedido));
		Cliente cliente2 = new Cliente("Alex Pacheco","1254869735");
		Pedido pedido2 = new Pedido(cliente2);
		pedido2.agregarItems(new ItemsPedido(2,producto,pedido2));
		pedido2.agregarItems(new ItemsPedido(1,producto3,pedido2));
		
		try {
			em.getTransaction().begin();
			em.persist(cliente);
			em.persist(cliente2);
			clienteDao.guardar(cliente);
			clienteDao.guardar(cliente2);
			em.persist(pedido);
			em.persist(pedido2);
			pedidoDao.guardar(pedido);
			pedidoDao.guardar(pedido2);
			em.getTransaction().commit();
			System.out.println("Cliente ingresado exitosamente con ID: " + cliente.getId());
			System.out.println("El pedido del cliente: "+cliente.getNombre()+" se ingreso correctamente con ID: "+pedido.getId());
			System.out.println("Cliente ingresado exitosamente con ID: " + cliente2.getId());
			System.out.println("El pedido del cliente: "+cliente2.getNombre()+" se ingreso correctamente con ID: "+pedido2.getId());
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		} 
		
		
		System.out.println(producto.getNombre());
		System.out.println(producto2.getNombre());
		BigDecimal precio = productoDao.consultaPrecioNombreProducto("Samsung");
		BigDecimal precio2 = productoDao.consultaPrecioNombreProducto("Xiaomi");
	    System.out.println(precio);
	    System.out.println(precio2);
	    //Impresion de los pedidos
	    List<Pedido> pedidos = pedidoDao.consultarTodos();
	    System.out.println("Número total de pedidos: "+pedidos.size());
	    
	    for (Pedido pedidoExterno:pedidos) {
			System.out.println("ID: "+ pedidoExterno.getId());
			System.out.println("Fecha: "+ pedidoExterno.getFecha());
			
			
			List<ItemsPedido> items = pedidoExterno.getItems();
			for (ItemsPedido item : items) {
				System.out.println("Producto: "+item.getProducto().getNombre());
				System.out.println("Cantidad: "+item.getCantidad());
				System.out.println("precio Unitario: "+item.getPrecioUnitario());
				System.out.println("Valor total del producto: "+item.getValorTotal());
				
				System.out.println("---------------------------------------------");
			}
			System.out.println("Valor Total del pedido: "+pedidoExterno.getValorTotal());
			System.out.println("=====================================");
		}
	    BigDecimal valorTotal2=pedidoDao.valorTotalVendido();
		System.out.println("El valor total de los pedidos: "+valorTotal2);
		
		List<RelatorioDeVenta> relatorio = pedidoDao.relatorioDeVentasVO();
		
		relatorio.forEach(System.out::println);
	     
	}

	private static void registrarProducto() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria hogares = new Categoria ("HOGAR");
		Producto celular = new Producto("Samsung", "telefono usado",new BigDecimal("1000"),celulares);
		Producto celular2 = new Producto("Xiaomi", "telefono nuevo",new BigDecimal("800"),celulares);
		Producto hogar = new Producto("Muebles sala", "nuevo", new BigDecimal("500"), hogares);
		Producto hogar2 = new Producto("Televisión Plasma", "nuevo", new BigDecimal("400"), hogares);
				
		
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDAO productoDao = new ProductoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
	
		em.getTransaction().begin();
		
		categoriaDao.guardar(celulares);
		productoDao.guardar(celular);
		productoDao.guardar(celular2);
		categoriaDao.guardar(hogares);
		productoDao.guardar(hogar);
		productoDao.guardar(hogar2);
		
		em.getTransaction().commit();
		
		em.close();
	}
		
}
