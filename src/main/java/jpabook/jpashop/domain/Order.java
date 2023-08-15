package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {
	@Id @GeneratedValue
	@Column(name="order_id")
	private  Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;

	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; //status of order(order, cancel)
	
	public void setMember(Member member) {
		this.member = member; //change the member's value
		member.getOrders().add(this); // change the member's value from member table
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem); //change orderItem's value
		orderItem.setOrder(this); // change orderItem's value from orderItem table
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery; // change delivery's value
		delivery.setOrder(this); // change delivary's value from delivery table
	}
}
