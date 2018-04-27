package be.vdab.toysforboys.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.entities.Product;
import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.exceptions.OutOfStockException;
import be.vdab.toysforboys.valueobjects.OrderDetail;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaOrderRepository.class)
@Sql("/insertOrder.sql")
public class JpaOrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private EntityManager manager;
	@Autowired
	private JpaOrderRepository repository;

	private long idOfTestOrder() {
		return super.jdbcTemplate.queryForObject("select id from orders where comments='test'", Long.class);
	}

	@Test
	public void read() {
		Order order = repository.read(idOfTestOrder()).get();
		assertEquals("test", order.getComments());
		assertEquals("test", order.getCustomer().getName());
	}
	@Test
	public void readOrderDetails() {
		Order order = repository.read(idOfTestOrder()).get();
		assertEquals(1, order.getOrderDetails().size());
		long productId = super.jdbcTemplate.queryForObject("select id from products where name='test'", Long.class);
		Product product = manager.find(Product.class, productId);
		assertTrue(order.getOrderDetails().contains(new OrderDetail(product,2,BigDecimal.valueOf(200))));
	}
	
	// Testen van de Order method ship
	@Test
	public void shipOrder() {
		Order order = repository.read(idOfTestOrder()).get();
		order.ship();
		assertTrue(order.getStatus() == Status.SHIPPED);
		assertTrue(order.getShippedDate().getDayOfWeek().equals(LocalDate.now().getDayOfWeek()));
		order.getOrderDetails().forEach(detail -> assertTrue(detail.getProduct().getQuantityInOrder() == 3 && detail.getProduct().getQuantityInStock() == 8));
	}
	@Test(expected = OutOfStockException.class)
	public void shipOrderWithQuantityOutOfStock() {
		manager.createNativeQuery("update orderdetails set quantityOrdered = 20 where orderId = :orderId")
			.setParameter("orderId", idOfTestOrder()).executeUpdate();
//		manager.flush();
		Order order = repository.read(idOfTestOrder()).get();
		order.ship();
	}
	
	@Test
	public void findUnshippedOrders() {
		List<Order> orders = repository.findUnshippedOrders();
		long numberOfOrders = super.countRowsInTableWhere("orders", "status not in ('shipped','cancelled')");
		assertEquals(numberOfOrders, orders.size());
		orders.forEach(order -> {
			assertNotEquals(order.getStatus(), Status.CANCELLED);
			assertNotEquals(order.getStatus(), Status.SHIPPED);
		});
	}

}
