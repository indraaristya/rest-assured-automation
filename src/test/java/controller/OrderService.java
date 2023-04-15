package controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private List<Order> orderArray = new ArrayList<>();

    public int createNewOrder(OrderRequest order) {
        Order newOrder = new Order();
        newOrder.setId(order.getOrderId());
        newOrder.setDescription(order.getDesc());
        newOrder.setStatus(order.getStatus());
        newOrder.setTimestamp(order.getUpdatedTime());
        newOrder.setSpecialOrder(order.getSpecialOrder());

        orderArray.add(newOrder);

        return 1;
    }

    public List<Order> getAllOrder() {
        return this.orderArray;
    }
}
