package be.vdab.toysforboys.repositories;

import java.util.Optional;

import be.vdab.toysforboys.entities.Customer;

public interface CustomerRepository {
	Optional<Customer> read(long id);
}
