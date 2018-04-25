package be.vdab.toysforboys.entities;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import be.vdab.toysforboys.valueobjects.Address;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@Embedded
	private Address address;
	@Version
	private long version;
	
	protected Customer() {
	}
	public Customer(String name, Address address) {
		this.name = name;
		this.address = address;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Address getAddress() {
		return address;
	}
	
}
