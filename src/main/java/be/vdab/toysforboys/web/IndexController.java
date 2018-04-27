package be.vdab.toysforboys.web;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.toysforboys.exceptions.ShippingFailedException;
import be.vdab.toysforboys.services.OrderService;

@Controller
@RequestMapping("/")
class IndexController {
	private final OrderService orderService;
	private static final String VIEW = "index";
	private static final String REDIRECT_AFTER_SHIPPING = "redirect:/";
	
	IndexController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping
	ModelAndView showUnshippedOrders() {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		modelAndView.addObject("unshippedOrders", orderService.findUnshippedOrders());
		return modelAndView;
	}
	
	@PostMapping(value="shiporders")
	String ship(@RequestParam(required = false) long[] shipids, RedirectAttributes redirectAttributes) {
		Set<Long> failed = new LinkedHashSet<>();
		if (shipids != null) {
			for (long id: shipids) {
				try {
					orderService.ship(id);
				} catch (ShippingFailedException ex) {
					failed.add(id);
				}
			}
		} 
		if (! failed.isEmpty()) {
			String failedOrders = "";
			for (Long id: failed) {
				failedOrders += id + ", ";
			}
			redirectAttributes.addAttribute("fout", "Shipping failed for order(s) " + failedOrders + "not enough stock");
		}
		return REDIRECT_AFTER_SHIPPING;
	}
}
