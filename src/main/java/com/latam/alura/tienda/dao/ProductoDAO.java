package com.latam.alura.tienda.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Producto;

public class ProductoDAO {
	
	private EntityManager em;

	public ProductoDAO(EntityManager em) {
		this.em = em;	
	}
	
	public void guardar(Producto producto) {
		this.em.persist(producto);
	}
	
	public Producto consultaPorId(Long id) {
		return em.find(Producto.class, id);
	}
	
	public List<Producto> consultarTodos(){
		String jqpl="SELECT P FROM Producto AS P";
		return em.createQuery(jqpl,Producto.class).getResultList();
	}
	
	public List<Producto> consultaPorNombre(String nombre){
		String jqpl="SELECT P FROM Producto AS P WHERE P.nombre=:nombre";
		return em.createQuery(jqpl).setParameter("nombre", nombre).getResultList();
	}
	public List<Producto> consultaPorNombreCategoria (String categoria){
		String jqpl="SELECT P FROM Producto AS P WHERE P.categoria.nombre=:nombre";
		return em.createQuery(jqpl).setParameter("nombre", categoria).getResultList();
	}
}
