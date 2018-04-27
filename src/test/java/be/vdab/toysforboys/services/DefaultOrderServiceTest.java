package be.vdab.toysforboys.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.vdab.toysforboys.entities.Country;
import be.vdab.toysforboys.entities.Customer;
import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.exceptions.ShippingFailedException;
import be.vdab.toysforboys.repositories.OrderRepository;
import be.vdab.toysforboys.valueobjects.Address;

@RunWith(MockitoJUnitRunner.class)
public class DefaultOrderServiceTest {
	private DefaultOrderService service;
	@Mock
	private OrderRepository repository;
	private Order order;
	@Before
	public void before() {
		Country country = new Country("test");
		Address address = new Address("test", "test", "test", "test", country);
		Customer customer = new Customer("test", address);
		order = new Order(LocalDate.now(), LocalDate.now(), LocalDate.now(), "test", customer, Status.PROCESSING);
		when(repository.read(1)).thenReturn(Optional.of(order));
		service = new DefaultOrderService(repository);
	}
	@Test
	public void ship() {
		service.ship(1);
		assertEquals(Status.SHIPPED, order.getStatus());
		verify(repository).read(1);
	}
	@Test(expected = ShippingFailedException.class)
	public void shippingFailed() {
		
	}
}
