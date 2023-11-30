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

public class RegistroDeProducto {

	public static void main(String[] args) {
		registrarProducto();
		EntityManager em = JPAUtils.getEntityManager();	
		ProductoDAO productoDao= new ProductoDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		PedidoDAO pedidoDao= new PedidoDAO(em);
		Producto producto = productoDao.consultaPorId(1l);
		Producto producto2 = productoDao.consultaPorId(2l);
		
		Cliente cliente = new Cliente("Andres Panchi", "1754099686");
		Pedido pedido = new Pedido(cliente);
		pedido.agregarItems(new ItemsPedido(5,producto2,pedido));
		
		em.getTransaction().begin();
		clienteDao.guardar(cliente);
		
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		pedidoDao.guardar(pedido);
		em.getTransaction().commit();
		
		List<Cliente> clienteL=clienteDao.consultaPorNombre("Andres");
		
		
		System.out.println(producto.getNombre());
		System.out.println(producto2.getNombre());
		BigDecimal precio = productoDao.consultaPrecioNombreProducto("Redsem");
		BigDecimal precio2 = productoDao.consultaPrecioNombreProducto("Xiaomi");
	    System.out.println(precio);
	    System.out.println(precio2);
		
		BigDecimal valorTotal=pedidoDao.valorTotalVendido();
		System.out.println("Valor total= "+valorTotal);
		System.out.println(clienteL);
		
	}

	private static void registrarProducto() {
		Categoria celulares = new Categoria("CELULARES");
		Producto celular = new Producto("Redsem", "telefono usado",new BigDecimal("1000"),celulares);
		Producto celular2 = new Producto("Xiaomi", "telefono nuevo",new BigDecimal("800"),celulares);		
		
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDAO productoDao = new ProductoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
	
		em.getTransaction().begin();
		
		categoriaDao.guardar(celulares);
		productoDao.guardar(celular);
		productoDao.guardar(celular2);
		
		em.getTransaction().commit();
		
		em.close();
	}
		
}
