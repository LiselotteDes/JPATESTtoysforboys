package be.vdab.toysforboys.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.toysforboys.services.OrderService;

@Controller
@RequestMapping("orders")
class OrderController {
	private final OrderService orderService;
	private static final String VIEW = "order";
	
	OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("{id}")
	ModelAndView order(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		orderService.read(id).ifPresent(order -> modelAndView.addObject(order));
		return modelAndView;
	}
}
