package tacos.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.model.Order;
import tacos.model.User;
import tacos.props.OrderProps;
import tacos.repository.OrderRepository;
import tacos.repository.UserRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	private OrderProps orderProps;

	private OrderRepository orderRepo;

	@Autowired
	public OrderController(OrderRepository orderRepo, OrderProps orderProps) {
		this.orderRepo = orderRepo;
		this.orderProps = orderProps;

	}

	@GetMapping("current")
	public String orderForm() {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
			@AuthenticationPrincipal User user) {

		if (errors.hasErrors()) {
			return "orderForm";
		}

		// way1 - inject Principal priciple
		// userRepo.findByUsername(rpinciple.getName());
		// way2 - inject Authentication auth
		// User user = (User) authentication.getPrincipal();
		// way3 -- @AuthenticationPrincipal User user)
		// way4 --
		// Authentication authentication =
		// SecurityContextHolder.getContext().getAuthentication();
		// User user = (User) authentication.getPrincipal();
		order.setUser(user);
		this.orderRepo.save(order);

		sessionStatus.setComplete();
		log.info("Order submitted:" + order);
		return "redirect:/";
	}

	@GetMapping
	public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
		Pageable pageable = PageRequest.of(0, this.orderProps.getPageSize());
		model.addAttribute("orders", this.orderRepo.findByUserOrderByPlacedAt(user, pageable));
		return "orderList";
	}
}
