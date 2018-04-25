package be.vdab.toysforboys.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import org.springframework.format.annotation.NumberFormat;

import be.vdab.toysforboys.entities.Product;

@Embeddable
@NamedEntityGraph(name = OrderDetail.MET_PRODUCT, attributeNodes = @NamedAttributeNode("product"))
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String MET_PRODUCT = "OrderDetail.metProduct";
	@ManyToOne(optional = false)
	@JoinColumn(name = "productId")
	private Product product;
	private long quantityOrdered;
	@NumberFormat(pattern = "0.00")
	private BigDecimal priceEach;
	
	protected OrderDetail() {
	}
	public OrderDetail(Product product, long quantityOrdered, BigDecimal priceEach) {
		this.product = product;
		this.quantityOrdered = quantityOrdered;
		this.priceEach = priceEach;
	}
	public Product getProduct() {
		return product;
	}
	public long getQuantityOrdered() {
		return quantityOrdered;
	}
	public BigDecimal getPriceEach() {
		return priceEach;
	}
	@NumberFormat(pattern = "0.00")
	public BigDecimal getValue() {
		return priceEach.multiply(BigDecimal.valueOf(quantityOrdered));
	}
	public boolean isStockEnough() {
		return product.getQuantityInStock() >= this.quantityOrdered;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OrderDetail))
			return false;
		OrderDetail other = (OrderDetail) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
}
