package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id @GeneratedValue
	@Column(name="order_id")
	private  Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
	
	// == create method == 
	public static Order createOrder(Member member, Delivery delivery,  OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for(OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		
		return order;
	}
	
	// cancel the order
	public void cancel() {
		if(delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송이 완료된 상품은 취소가 불가능합니다.");
		}
		
		//dirty checking 발생(*dirty checking: JPA가 관리하고 있는 영속상태의 객체는 데이터 변경감지 범위에 있기 때문에 데이터가 변경이 되면 JPA가 변경된 데이터를 update하는 것.) 
		this.setStatus(OrderStatus.CANCEL);
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();
			
		}
	}
	
	public int getTotalPrice() {
		int totalPrice  = 0;
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		
		return totalPrice;
	}
}
