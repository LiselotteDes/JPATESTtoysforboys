package be.vdab.toysforboys.valueobjects;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import be.vdab.toysforboys.entities.Country;

@Embeddable
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	private String streetAndNumber;
	private String city;
	private String state;
	private String postalCode;
	@ManyToOne(optional = false)
	@JoinColumn(name = "countryId")
	private Country country;
	
	protected Address() {
	}

	public Address(String streetAndNumber, String city, String state, String postalCode, Country country) {
		this.streetAndNumber = streetAndNumber;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
	}

	public String getStreetAndNumber() {
		return streetAndNumber;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public Country getCountry() {
		return country;
	}
	
}
