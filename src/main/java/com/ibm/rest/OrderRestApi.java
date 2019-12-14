package com.ibm.rest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import com.ibm.model.Order;
import com.ibm.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderRestApi {
    OrderService orderService;

    @Autowired
    public OrderRestApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/restaurants/{rid}/orders", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Order createOder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }
}
