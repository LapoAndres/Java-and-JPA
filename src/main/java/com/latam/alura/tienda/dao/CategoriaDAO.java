package com.latam.alura.tienda.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Pedido;

public class CategoriaDAO {
	
	private EntityManager em;

	public CategoriaDAO(EntityManager em) {
		this.em = em;	
	}
	
	public void guardar(Categoria categoria) {
		this.em.persist(categoria);
	}
	
	public void actualizar(Categoria categoria) {
		this.em.merge(categoria);
	}
	
	public void remover(Categoria categoria) {
		categoria=this.em.merge(categoria);
		this.em.remove(categoria);
	}
	
	public Categoria consultaPorNombre(String nombre){
		String jpql =" SELECT C FROM Pedido AS C WHERE C.nombre=:nombre ";
		return em.createQuery(jpql,Categoria.class).setParameter("nombre", nombre).getSingleResult();
	}
	
}
