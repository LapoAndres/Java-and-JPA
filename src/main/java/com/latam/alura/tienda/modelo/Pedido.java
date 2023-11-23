package com.latam.alura.tienda.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="pedidos")
public class Pedido {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private LocalDate fecha=LocalDate.now();
	private BigDecimal valorTotal;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy="pedido")
	private List<ItemsPedido> items= new ArrayList<>();
	
	public Pedido(Cliente cliente) {
		this.cliente = cliente;
	}
	public Pedido() {
		//constructor vacio necesario para el metodo merge
	}
	
	public void agregarItems(ItemsPedido item) {
		item.setPedido(this);
		this.items.add(item);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
