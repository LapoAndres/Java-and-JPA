package com.latam.alura.tienda.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Cliente cliente;
	
	@OneToMany(mappedBy="pedido", cascade=CascadeType.ALL)
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
		calcularValorTotal();
	}
	
	public void calcularValorTotal() {
		this.valorTotal = this.items.stream().map(ItemsPedido::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public List<ItemsPedido> getItems() {
		return items;
	}
	
	public void setItems(List<ItemsPedido> items) {
		this.items = items;
	}
	public Cliente getCliente() {
		return cliente;
	}
}
