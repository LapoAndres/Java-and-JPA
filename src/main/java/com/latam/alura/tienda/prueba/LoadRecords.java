package com.latam.alura.tienda.prueba;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

public class LoadRecords {
 public static void cargarRegistros() throws FileNotFoundException {
	 EntityManager em=JPAUtils.getEntityManager();
	 CategoriaDAO categoriaDAO = new CategoriaDAO(em);
	 ProductoDAO productoDAO= new ProductoDAO(em);
	 ClienteDAO clienteDAO=new ClienteDAO(em);
	 PedidoDAO pedidoDAO=new PedidoDAO(em);
	 em.getTransaction().begin();
	 
	 loadCategoria("categoria",categoriaDAO,em);
	 
	 loadProducto("producto",productoDAO,categoriaDAO,em);
	 
	 loadCliente("cliente",clienteDAO,em);
	 
	 List<Cliente> clientesList = clienteDAO.consultarTodos();
	 List<Pedido> pedidoList = new ArrayList<>();
	 
	 for (Cliente cl:clientesList) {
		 pedidoList.add(new Pedido(cl));
	 }
	 
	 for (int i = 0; i<pedidoList.size(); i++) {
		pedidoList.get(i).agregarItems(new ItemsPedido(i+1,productoDAO.consultaPorId((long) (i+1)),pedidoList.get(i)));
		pedidoDAO.guardar(pedidoList.get(i));
	}
	 
	 em.getTransaction().commit();
	 em.close();
 }
 
 private static void loadProducto(String type, ProductoDAO productoDAO,CategoriaDAO categoriaDAO,EntityManager em) throws FileNotFoundException {
	 List<String> productosTxt = readFile(type);
	 for (int i = 0; i < productosTxt.size(); i++) {
		String[] line= productosTxt.get(i).split(";");
		if (line.length>1) {
			Categoria categoria=categoriaDAO.consultaPorNombre(line[3]);
			Producto producto = new Producto(line[4],line[0],new BigDecimal(line[1]),categoria);
			productoDAO.guardar(producto);
		}
	}
 }
 
 private static void loadCategoria(String type, CategoriaDAO categoriaDAO, EntityManager em) throws FileNotFoundException{
	 List<String> categoriasTxt = readFile(type);
	 for (int i = 0; i < categoriasTxt.size(); i++) {
		String[] line= categoriasTxt.get(i).split(";");
		if (line.length==1) {
			Categoria categoria=new Categoria(categoriasTxt.get(i));
			categoriaDAO.guardar(categoria);
		}
	}
 }
 
 private static void loadCliente(String type, ClienteDAO clienteDAO,EntityManager em) throws FileNotFoundException {
		List<String> clientesTxt =readFile(type);		
		for(int i=0;i<clientesTxt.size();i++) {
			String[] line = clientesTxt.get(i).split("~");
			System.out.println(line[0]+line[1]);
			if(line.length>1) {
				Cliente cliente= new Cliente(line[0],line[1]);
				clienteDAO.guardar(cliente);
				em.flush();	
			}
		}
	}

	private static List<String> readFile(String type) throws FileNotFoundException {
		URL resource= LoadRecords.class.getClassLoader().getResource("META-INF/"+type+".txt");
		
		if (resource == null) {
			throw new FileNotFoundException("Archivo no encontrado");
		}
		
		File file = new File(resource.getFile());
		Scanner scan = new Scanner(file);
		List<String> pedido= new ArrayList<>();
		while(scan.hasNextLine()){
			pedido.add(scan.nextLine());
		}
		scan.close();
		return pedido;
	}
	
}
