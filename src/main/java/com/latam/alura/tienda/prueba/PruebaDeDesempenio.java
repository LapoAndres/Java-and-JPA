package com.latam.alura.tienda.prueba;

import java.io.FileNotFoundException;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.modelo.Pedido;
import com.latam.alura.tienda.utils.JPAUtils;

public class PruebaDeDesempenio {

	public static void main(String[] args) throws FileNotFoundException {
		//LoadRecords.cargarRegistros();
		
		EntityManager em = JPAUtils.getEntityManager();
		
		Pedido pedido = em.find(Pedido.class, 1l);
		
		System.out.println(pedido.getFecha());
		

	}

}
