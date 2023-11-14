package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.CategoriaDAO;
import com.latam.alura.tienda.dao.ProductoDAO;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {
		Categoria celulares = new Categoria("CELULARES");
		//Producto celular = new Producto("Samsung", "telefono usado",new BigDecimal("1000"),celulares);
		
		EntityManager em = JPAUtils.getEntityManager();
		
		//ProductoDAO productoDao= new ProductoDAO(em);
		//CategoriaDAO categoriaDao = new CategoriaDAO(em);
		
		em.getTransaction().begin();
		
		//categoriaDao.guardar(celulares);
		//productoDao.guardar(celular);
		
		 em.persist(celulares);	 //para generar una persistencia
		 celulares.setNombre("LIBROS");
		 em.flush();
		 em.clear();
		//em.getTransaction().commit();
		//em.close();
		 celulares=em.merge(celulares); //con el merge JPA necesita realizar un select dentro de la base de datos para poder realizar modificaciones
		 celulares.setNombre("SOFTWARES");
		 em.flush();
		 em.remove(celulares);
		 em.flush();
	}

}
