package com.novianto.challange5.service.impl;

import com.novianto.challange5.dto.OrderDto;
import com.novianto.challange5.entity.Order;
import com.novianto.challange5.entity.User;
import com.novianto.challange5.repository.OrderRepository;
import com.novianto.challange5.repository.UserRepository;
import com.novianto.challange5.service.OrderService;
import com.novianto.challange5.util.ConfigValidation;
import com.novianto.challange5.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Response response;

    @Override
    public Page<Order> getAllOrder(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Map<String, Object> saveOrder(OrderDto orderDto) {
        Map<String, Object> responseMap = new HashMap<>();

        if (orderDto == null) {
            return response.errorResponse(ConfigValidation.ORDER_DATA_INVALID, ConfigValidation.STATUS_CODE_BAD_REQUEST);
        } else {
            if (!response.isValidDate(orderDto.getOrderTime())) {
                return response.errorResponse(ConfigValidation.ORDER_TIME_NOT_VALID, ConfigValidation.STATUS_CODE_BAD_REQUEST);
            } else if (!response.defaultIfNull(orderDto.isCompleted())) {
                return response.errorResponse(ConfigValidation.ORDER_COMPLETED_NOT_VALID, ConfigValidation.STATUS_CODE_BAD_REQUEST);
            }
        }

        try {
            Order order = new Order();
            order.setId(UUID.randomUUID());
            order.setOrderTime(orderDto.getOrderTime());
            order.setDestinationAddress(orderDto.getDestinationAddress());
            order.setCompleted(orderDto.isCompleted());

            if (orderDto.getUser() == null) {
                return response.errorResponse(ConfigValidation.USER_REQUIRED, ConfigValidation.STATUS_CODE_BAD_REQUEST);
            }

            UUID userId = orderDto.getUser().getId();
            Optional<User> userOptional = userRepository.findById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                order.setUser(user.getId());
                Order savedOrder = orderRepository.save(order);
                responseMap = response.successResponse(savedOrder);
            } else {
                return response.errorResponse(ConfigValidation.ID_MERCHANT_NOT_FOUND, ConfigValidation.STATUS_CODE_NOT_FOUND);
            }
        } catch (DataAccessException e) {
            responseMap = response.errorResponse(e.getMessage(), ConfigValidation.STATUS_CODE_INTERNAL_SERVER_ERROR);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> updateOrder(UUID idOrder, OrderDto orderDto) {
        Map<String, Object> responseMap = new HashMap<>();

        try {
            Optional<Order> existingOrder = Optional.ofNullable(orderRepository.getByIdOrder(idOrder));

            if (existingOrder.isPresent()) {
                Order updatedOrder = existingOrder.get();
                if (orderDto.getOrderTime() != null) {
                    updatedOrder.setOrderTime(orderDto.getOrderTime());
                }
                if (orderDto.getDestinationAddress() != null) {
                    updatedOrder.setDestinationAddress(orderDto.getDestinationAddress());
                }
                updatedOrder.setCompleted(orderDto.isCompleted());

                orderRepository.save(updatedOrder);
                responseMap = response.successResponse(updatedOrder);
            } else {
                responseMap = response.errorResponse(ConfigValidation.ID_ORDER_NOT_FOUND, ConfigValidation.STATUS_CODE_NOT_FOUND);
            }
        } catch (Exception e) {
            responseMap = response.errorResponse(e.getMessage(), ConfigValidation.STATUS_CODE_INTERNAL_SERVER_ERROR);
        }

        return responseMap;
    }

    @Override
    public Map<String, Object> deleteOrder(UUID idOrder) {
        Map<String, Object> responseMap = new HashMap<>();

        try {
            Optional<Order> orderToDelete = Optional.ofNullable(orderRepository.getByIdOrder(idOrder));

            if (orderToDelete.isPresent()) {
                Order order = orderToDelete.get();
                order.setDeleted_date(new Date());
                orderRepository.save(order);

                responseMap = response.successResponse(ConfigValidation.SUCCESS);
            } else {
                responseMap = response.errorResponse(ConfigValidation.ID_ORDER_NOT_FOUND, ConfigValidation.STATUS_CODE_NOT_FOUND);
            }
        } catch (Exception e) {
            responseMap = response.errorResponse(e.getMessage(), ConfigValidation.STATUS_CODE_INTERNAL_SERVER_ERROR);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> getOrderById(UUID idOrder) {
        Map<String, Object> responseMap = new HashMap<>();

        Optional<Order> orderOptional = Optional.ofNullable(orderRepository.getByIdOrder(idOrder));

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            responseMap = response.successResponse(order);
        } else {
            responseMap = response.errorResponse(ConfigValidation.ID_ORDER_NOT_FOUND, ConfigValidation.STATUS_CODE_NOT_FOUND);
        }
        return responseMap;
    }
}
