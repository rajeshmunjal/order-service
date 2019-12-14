package com.ibm.service.impl;

import org.junit.Before;
import org.junit.Test;

import com.ibm.model.ItemQuantity;
import com.ibm.model.Order;
import com.ibm.model.UserInfo;
import com.ibm.repository.OrderRepository;
import com.ibm.service.impl.OrderServiceImpl;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    private OrderRepository orderRepository;
    private RestTemplate restTemplate;
    private OrderServiceImpl orderService;
    private final String restaurantId = "11111111-1111-1111-11111111111111111";
    private UserInfo userInfo;

    @Before
    public void setupMock() {
        orderRepository = mock(OrderRepository.class);
        restTemplate = mock(RestTemplate.class);
        orderService = new OrderServiceImpl(orderRepository, restTemplate);
        userInfo = new UserInfo();
        userInfo.setFirstName("Rajesh");
        userInfo.setLastName("Munjal");
        userInfo.setPhone("2016730678");

    }

    @Test
    public void whenCreateOrder_returnCreatedOrder() {
        List<ItemQuantity> itemQuantities = new ArrayList<>();
        itemQuantities.add(generateItemQuantity("Menu item 1", 11, 2));
        itemQuantities.add(generateItemQuantity("Menu item 2", 12, 3));
        Order order = generateOrder(restaurantId, itemQuantities, "Special", userInfo);
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.createOrder(order);
        assertThat(savedOrder.getTotalPrice()).isEqualTo(2 * 11 + 3 * 12);
        assertThat(savedOrder.getOrderTime()).isGreaterThan(0);
    }

    private Order generateOrder(String restaurantId, List<ItemQuantity> items, String specialNote, UserInfo userInfo) {
        Order order = new Order();
        order.setRestaurantId(restaurantId);
        order.setItems(items);
        order.setSpecialNote(specialNote);
        return order;
    }

    private ItemQuantity generateItemQuantity(String name, int price, int quantity) {
        ItemQuantity itemQuantity = new ItemQuantity();
        itemQuantity.setName(name);
        itemQuantity.setPrice(price);
        itemQuantity.setQuantity(quantity);
        return itemQuantity;
    }
}
