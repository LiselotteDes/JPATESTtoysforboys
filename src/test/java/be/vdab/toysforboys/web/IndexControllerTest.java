package be.vdab.toysforboys.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.toysforboys.services.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {
	private IndexController controller;
	@Mock
	private OrderService dummyOrderService;
	
	@Before
	public void before() {
		controller = new IndexController(dummyOrderService);
	}
	@Test
	public void showUnshippedOrdersWerktSamenMetJuisteJSP() {
		ModelAndView modelAndView = controller.showUnshippedOrders();
		assertEquals("index", modelAndView.getViewName());
	}
	@Test
	public void showUnshippedOrdersGeeftUnshippedOrdersDoor() {
		ModelAndView modelAndView = controller.showUnshippedOrders();
		assertTrue(modelAndView.getModel().containsKey("unshippedOrders"));
	}
}
