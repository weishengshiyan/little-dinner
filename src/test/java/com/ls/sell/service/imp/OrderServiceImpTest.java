package com.ls.sell.service.imp;

import com.ls.sell.dataobject.OrderDetail;
import com.ls.sell.dto.OrderDTO;
import com.ls.sell.enums.OrderStatusEnum;
import com.ls.sell.enums.PayStatusEnum;
import com.ls.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImpTest {

    private static final String ORDERID="1562423934958719624";

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID="123456";
    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("腰痛");
        orderDTO.setBuyerAddress("上海");
        orderDTO.setBuyerPhone("12345678923");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList=new ArrayList<>();

        OrderDetail o1=new OrderDetail();
        o1.setProductId("123457");
        o1.setProductQuantity(2);
        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result =orderService.create(orderDTO);
        log.info("【创建订单】 result{}",result);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne(ORDERID);
        log.info("【查询单个订单】 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findList() {

        PageRequest request=PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findList(BUYER_OPENID,request);
        Assert.assertNotNull(orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO =orderService.findOne(ORDERID);
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());

    }

    @Test
    @Transactional
    public void finish() {
        OrderDTO orderDTO =orderService.findOne(ORDERID);
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(), result.getOrderStatus());
    }

    @Test
    @Transactional
    public void paid() {
        OrderDTO orderDTO =orderService.findOne(ORDERID);
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getOrderStatus());

    }
}