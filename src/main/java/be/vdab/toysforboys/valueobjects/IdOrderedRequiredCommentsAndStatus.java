package be.vdab.toysforboys.valueobjects;

import java.time.LocalDate;

import be.vdab.toysforboys.entities.Customer;
import be.vdab.toysforboys.enums.Status;

public class IdOrderedRequiredCommentsAndStatus {
	private final long id;
	private final LocalDate ordered;
	private final LocalDate required;
	private final Customer customer;
	private final Status status;
	
	public IdOrderedRequiredCommentsAndStatus(long id, LocalDate ordered, LocalDate required, Customer customer,
			Status status) {
		this.id = id;
		this.ordered = ordered;
		this.required = required;
		this.customer = customer;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public LocalDate getOrdered() {
		return ordered;
	}

	public LocalDate getRequired() {
		return required;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Status getStatus() {
		return status;
	}
	
}
