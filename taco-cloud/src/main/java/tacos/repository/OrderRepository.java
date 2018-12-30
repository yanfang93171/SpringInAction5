package tacos.repository;

import tacos.model.Order;

public interface OrderRepository {

	public Order save(Order order);
}
