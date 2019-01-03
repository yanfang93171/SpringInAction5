package tacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.model.Order;
import tacos.props.OrderProps;
import tacos.repository.OrderRepository;

@RestController
@RequestMapping(path = "/orders", consumes = "application/json")
public class OrderController {

	private OrderProps orderProps;

	private OrderRepository orderRepo;

	@Autowired
	public OrderController(OrderRepository orderRepo, OrderProps orderProps) {
		this.orderRepo = orderRepo;
		this.orderProps = orderProps;

	}

	// @PutMapping is update resoruce -> 'REPLACEMENT'
	@PutMapping("/{orderId}")
	public Order putOrder(@RequestBody Order order) {
		return this.orderRepo.save(order);
	}

	// @PatchMapping is also updating resource -> 'partical update'
	@PatchMapping(path = "/{orderId}", consumes = "application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
		Order order = this.orderRepo.findById(orderId).get();
		if (patch.getDeliveryName() != null) {
			order.setDeliveryName(patch.getDeliveryName());
		}
		if (patch.getDeliveryStreet() != null) {
			order.setDeliveryStreet(patch.getDeliveryStreet());
		}
		if (patch.getDeliveryCity() != null) {
			order.setDeliveryCity(patch.getDeliveryCity());
		}
		if (patch.getDeliveryState() != null) {
			order.setDeliveryState(patch.getDeliveryState());
		}
		if (patch.getDeliveryZip() != null) {
			order.setDeliveryZip(patch.getDeliveryState());
		}
		if (patch.getCcNumber() != null) {
			order.setCcNumber(patch.getCcNumber());
		}
		if (patch.getCcExpiration() != null) {
			order.setCcExpiration(patch.getCcExpiration());
		}
		if (patch.getCcCVV() != null) {
			order.setCcCVV(patch.getCcCVV());
		}
		return this.orderRepo.save(order);
	}

	@DeleteMapping("/{orderId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") Long orderId) {
		try {
			this.orderRepo.deleteById(orderId);
		} catch (EmptyResultDataAccessException e) {
		}
	}
}
