package tacos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tacos.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	public List<Order> findByDeliveryZip(String deliveryZip);

	public List<Order> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);

	@Query("from Order o where o.deliveryCity='Seattle'")
	public List<Order> readOrdersDeliveredInSeattle();

	}
