package be.vdab.toysforboys.services;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(DefaultOrderService.class)
@ComponentScan(basePackageClasses = be.vdab.toysforboys.repositories.JpaOrderRepositoryTest.class)
@Sql("/insertOrder.sql")
public class DefaultOrderServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private DefaultOrderService service;
	@Autowired
	private EntityManager manager;
	
	private long idOfTestOrder() {
		return super.jdbcTemplate.queryForObject("select id from orders where comments='test'", Long.class);
	}

	@Test
	public void ship() {
		long id = idOfTestOrder();
		service.ship(id);
		manager.flush();
		LocalDate shipped = super.jdbcTemplate.queryForObject("select shippedDate from orders where id=?", LocalDate.class, id);
		assertEquals(LocalDate.now().getDayOfMonth(), shipped.getDayOfMonth());
	}

}
