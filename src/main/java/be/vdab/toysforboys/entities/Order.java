package be.vdab.toysforboys.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.exceptions.OutOfStockException;
import be.vdab.toysforboys.valueobjects.OrderDetail;

@Entity
@Table(name = "orders")
@NamedEntityGraph(name = Order.WITH_CUSTOMER, attributeNodes = @NamedAttributeNode("customer"))
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String WITH_CUSTOMER = "Order.withCustomer";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@DateTimeFormat(style = "S-", pattern = "dd-mm-yyyy")
	private LocalDate orderDate;
	@DateTimeFormat(style = "S-", pattern = "dd-mm-yyyy")
	private LocalDate requiredDate;
	private LocalDate shippedDate;
	private String comments;
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId")
	private Customer customer;
	@Enumerated(EnumType.STRING)
	private Status status;
	@ElementCollection
	@CollectionTable(name = "orderdetails", joinColumns = @JoinColumn(name = "orderId"))
	private Set<OrderDetail> orderDetails = new LinkedHashSet<>();
	@Version
	private long version;
	
	protected Order() {
	}

	public Order(LocalDate orderDate, LocalDate requiredDate, LocalDate shippedDate, String comments, Customer customer,
			Status status) {
		this.orderDate = orderDate;
		this.requiredDate = requiredDate;
		this.shippedDate = shippedDate;
		this.comments = comments;
		this.customer = customer;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public LocalDate getRequiredDate() {
		return requiredDate;
	}

	public LocalDate getShippedDate() {
		return shippedDate;
	}

	public String getComments() {
		return comments;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Status getStatus() {
		return status;
	}
	
	public Set<OrderDetail> getOrderDetails() {
		return Collections.unmodifiableSet(orderDetails);
	}
	
	public BigDecimal getValue() {
		return orderDetails.stream().map(detail -> detail.getValue())
				.reduce(BigDecimal.ZERO, (vorigeSom, waarde) -> vorigeSom.add(waarde));
	}
	
	private boolean isDeliverable() {
		return orderDetails.stream().allMatch(detail -> detail.isDeliverable());
	}
	
	public void ship() {
		if (! isDeliverable()) {
			throw new OutOfStockException();
		}
		status = Status.SHIPPED;
		shippedDate = LocalDate.now();
		for (OrderDetail detail : orderDetails) {
			long quantityOrdered = detail.getQuantityOrdered();
			Product product = detail.getProduct();
			product.ship(quantityOrdered);
		}
	}
}
