package com.ibm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.model.Order;
import com.ibm.repository.OrderRepository;
import com.ibm.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private RestTemplate restTemplate;
    Order completedOrder;
    String orderCompleteUpdater = "http://order-complete-updater";
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }
    
    @Override
    public Order createOrder(Order order) {
        order.setOrderTime(System.currentTimeMillis());
        order.setTotalPrice(order.getItems().stream().mapToInt(e -> e.getPrice() * e.getQuantity()).sum());
        completedOrder = orderRepository.save(order);
        restTemplate.postForLocation(orderCompleteUpdater + "/api/orders", completedOrder);
        return completedOrder;
    }
}
