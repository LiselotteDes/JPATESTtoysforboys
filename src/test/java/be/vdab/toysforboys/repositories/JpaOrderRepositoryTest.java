package be.vdab.toysforboys.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.Before;
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

import be.vdab.toysforboys.entities.Country;
import be.vdab.toysforboys.entities.Customer;
import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.entities.Product;
import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.exceptions.OutOfStockException;
import be.vdab.toysforboys.valueobjects.Address;
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
	private Country country;
	private Address address;
	private Customer customer;
	private Order order;
	
	@Before
	public void before() {
		country = new Country("test");
		address = new Address("test", "test", "test", "test", country);
		customer = new Customer("test", address);
		order = new Order(LocalDate.now(), LocalDate.now(), LocalDate.now(), "test", customer, Status.PROCESSING);
	}
	
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

}
