package com.example.minor_project_01.service;

import com.example.minor_project_01.dto.*;
import com.example.minor_project_01.entity.*;
import com.example.minor_project_01.exception.NotFoundException;
import com.example.minor_project_01.repo.OrderRepo;
import com.example.minor_project_01.repo.ProductRepo;
import com.example.minor_project_01.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RedisTemplate<String, List<ProductDTO>> redisTemplate;

    public List<ProductDTO> getProductByKeyword(String keyword, Pageable pageable){
        String key = "Search : "+keyword+":"+pageable.getPageNumber()+":"+pageable.getPageSize();
        List<ProductDTO> result = redisTemplate.opsForValue().get(key);
        if(result == null){
            List<Product> productList = productRepo.findByNameContaining(keyword,pageable);
            result = new ArrayList<>();
            for(Product product: productList){
                ProductDTO productDTO = ProductDTO.buildDTOFromProduct(product);
                result.add(productDTO);
            }
            redisTemplate.opsForValue().set(key,result);
        }
        return result;
    }

    public ProductDTO getProduct(Long id){
        Product product = productRepo.findById(id).get();
        ProductDTO productDTO = ProductDTO.buildDTOFromProduct(product);
        return productDTO;
    }

    @Transactional
    public OrderDetailDto addToOrder(AddToOrderDto addToOrderDto) throws NotFoundException {
        Product product = productRepo.findById(addToOrderDto.getProductId()).orElseThrow(()->  new NotFoundException("Product Does not exist"));


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orderList = orderRepo.findByStatusAndUser(OrderStatus.DRAFT,user);
        Order existingOrder;
        if(!orderList.isEmpty()){
            existingOrder = orderList.get(0);
        }
        else {
            existingOrder = Order.builder()
                    .status(OrderStatus.DRAFT)
                    .totalAmount(0.0)
                    .user(user)
                    .orderItems(new ArrayList<>())
                    .build();
        }
        OrderItem orderItem = OrderItem.builder()
                .order(existingOrder)
                .price(product.getPrice())
                .quantity(addToOrderDto.getQuantity())
                .product(product)
                .build();
        existingOrder.getOrderItems().add(orderItem);
        if(product.getStock() < orderItem.getQuantity()){
            //
        }
        //Pricing
        double totalItemsPrice = product.getPrice() * orderItem.getQuantity();
        existingOrder.setTotalAmount(existingOrder.getTotalAmount() + totalItemsPrice);
        existingOrder = orderRepo.save(existingOrder);
        product.setStock(product.getStock()-orderItem.getQuantity());

        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(existingOrder.getId());
        orderDetailDto.setOrderTotalPrice(existingOrder.getTotalAmount());
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        for(OrderItem orderItem1 : existingOrder.getOrderItems()){
            orderItemDtoList.add(OrderItemDto.mapOrderItemToDto(orderItem1));
        }
        orderDetailDto.setOrderItems(orderItemDtoList);
        return orderDetailDto;
    }

    public ResponseDTO submitOrder(Long orderId){
        Order order = orderRepo.findById(orderId).get();
        ResponseDTO responseDTO = new ResponseDTO();
        if(order.getStatus().equals(OrderStatus.DRAFT)){
            order.setStatus(OrderStatus.PLACED);
            orderRepo.save(order);
            responseDTO.setMsg("Submitted the Order");
            responseDTO.setStatusCode("123-OS");
            /*
            Trigger Email to Seller.
            Seller Accept Order with Accept API.
             */
        }
        else {
            responseDTO.setMsg("Failed: The Order is not in DRAFT state");
            responseDTO.setStatusCode("145-OS");
        }
        return responseDTO;
    }

}
