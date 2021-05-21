package io.kagboton.tacoscloud.controller;


import io.kagboton.tacoscloud.domain.Order;
import io.kagboton.tacoscloud.domain.User;
import io.kagboton.tacoscloud.repository.OrderRepository;
import io.kagboton.tacoscloud.utils.OrdersProps;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepository;
    private OrdersProps ordersProps;

    public OrderController(OrderRepository orderRepository, OrdersProps ordersProps) {
        this.orderRepository = orderRepository;
        this.ordersProps = ordersProps;
    }

    @GetMapping("/current")
    public String showOrderForm(@AuthenticationPrincipal User user, @ModelAttribute Order order){

        // pre fill the input with current user information
        if(order.getDeliveryName() == null){
            order.setDeliveryName(user.getFullname());
        }
        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }
        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }
        if (order.getDeliveryState() == null) {
            order.setDeliveryState(user.getState());
        }
        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user){

        if (errors.hasErrors()){
            return "orderForm";
        }

        order.setUser(user);

        // save the order
        orderRepository.save(order);

        // reset the session. order object is not needed anymore
        sessionStatus.setComplete();

        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model){ // get an user orders
        Pageable pageable = PageRequest.of(0, ordersProps.getPageSize());
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}
