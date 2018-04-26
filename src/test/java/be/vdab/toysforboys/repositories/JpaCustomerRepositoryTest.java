package be.vdab.toysforboys.repositories;

import static org.junit.Assert.assertEquals;

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
import be.vdab.toysforboys.valueobjects.Address;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaCustomerRepository.class)
@Sql("/insertCustomer.sql")
public class JpaCustomerRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	
//	private static final String CUSTOMERS = "customers";
	@Autowired
	private JpaCustomerRepository repository;
	
	private long idOfTestCustomer() {
		return super.jdbcTemplate.queryForObject("select id from customers where name='test'", Long.class);
	}
	
	@Test
	public void read() {
		Customer customer = repository.read(idOfTestCustomer()).get();
		assertEquals("test", customer.getName());
		assertEquals("test", customer.getAddress().getCity());
		assertEquals("test", customer.getAddress().getCountry().getName());
	}

}
