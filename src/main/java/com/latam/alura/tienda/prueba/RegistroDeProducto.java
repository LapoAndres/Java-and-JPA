package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.CategoriaDAO;
import com.latam.alura.tienda.dao.ProductoDAO;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {
		registrarProducto();
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDAO productoDao= new ProductoDAO(em);
		Producto producto = productoDao.consultaPorId(1l);
		System.out.println(producto.getNombre());
		
		BigDecimal precio = productoDao.consultaPrecioNombreProducto("Samsung");
		System.out.println(precio);
		
	}

	private static void registrarProducto() {
		Categoria celulares = new Categoria("CELULARES");
		Producto celular = new Producto("Samsung", "telefono usado",new BigDecimal("1000"),celulares);
		
		
		
		EntityManager em = JPAUtils.getEntityManager();
		ProductoDAO productoDao= new ProductoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		
		em.getTransaction().begin();
		
		categoriaDao.guardar(celulares);
		productoDao.guardar(celular);
		
		em.getTransaction().commit();
		
		
		em.close();
	}
		
}
