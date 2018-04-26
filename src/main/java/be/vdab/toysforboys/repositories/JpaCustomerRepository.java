package be.vdab.toysforboys.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import be.vdab.toysforboys.entities.Customer;

@Repository
class JpaCustomerRepository implements CustomerRepository {
	private EntityManager manager;
	JpaCustomerRepository(EntityManager manager) {
		this.manager = manager;
	}
	@Override
	public Optional<Customer> read(long id) {
		return Optional.ofNullable(manager.find(Customer.class, id));
	}
}
