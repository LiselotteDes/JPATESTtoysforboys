package be.vdab.toysforboys.services;

import java.util.Optional;

import be.vdab.toysforboys.entities.Customer;

public interface CustomerService {
	Optional<Customer> read(long id);
}
