package be.vdab.toysforboys.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.exceptions.OutOfStockException;
import be.vdab.toysforboys.exceptions.ShippingFailedException;
import be.vdab.toysforboys.repositories.OrderRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultOrderService implements OrderService {
	
	private final OrderRepository repository;
	DefaultOrderService(OrderRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Optional<Order> read(long id) {
		return repository.read(id);
	}
	
	@Override
	public List<Order> findUnshippedOrders() {
		return repository.findUnshippedOrders();
	}
	
	@Override
	@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
	public void ship(long id) {
		try {
			repository.read(id).get().ship();
		} catch (OutOfStockException ex) {
			throw new ShippingFailedException();
		}
	}
}
