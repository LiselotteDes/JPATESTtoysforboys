package be.vdab.toysforboys.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "countries")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@Version
	private long version;
	
	protected Country() {
	}
	
	public Country(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
