package be.vdab.toysforboys.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import be.vdab.toysforboys.entities.Product;

public class OrderDetailTest {
	
	private OrderDetail orderDetail;
	private Product product;
	
	@Before
	public void before() {
		product = new Product("test", "test", "test", 10, 10, BigDecimal.ONE);
	}
	
	@Test
	public void valueGeeftAantalOrderedVermenigvuldigdMetPriceEach() {
		orderDetail = new OrderDetail(product, 10, BigDecimal.TEN);
		assertEquals(0, BigDecimal.valueOf(100).compareTo(orderDetail.getValue()));
	}

	@Test
	public void isStockEnoughLevertTrueAlsStockVoldoendeIs() {
		orderDetail = new OrderDetail(product, 5, BigDecimal.ONE);
		assertTrue(orderDetail.isStockEnough());
	}
	@Test
	public void isStockEnoughLevertFalseAlsErMeerOrderedIsDanInStock() {
		orderDetail = new OrderDetail(product, 11, BigDecimal.ONE);
		assertFalse(orderDetail.isStockEnough());
	}
	@Test
	public void isStockEnoughLevertTrueAlsErEvenveelBesteldWordtAlsErStockIs() {
		orderDetail = new OrderDetail(product, 10, BigDecimal.ONE);
		assertTrue(orderDetail.isStockEnough());
	}
}
