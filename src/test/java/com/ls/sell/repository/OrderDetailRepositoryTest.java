package com.ls.sell.repository;

import com.ls.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;


    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456");
        orderDetail.setOrderId("1");
        orderDetail.setProductId("12");
        orderDetail.setProductName("皮蛋");
        orderDetail.setProductPrice(new BigDecimal(4.7));
        orderDetail.setProductQuantity(12);
        orderDetail.setProductIcon("http://xxxx.jsp");
        OrderDetail save = repository.save(orderDetail);
        Assert.assertNotNull(save);

    }
    @Test
    public void findById(){
        Optional<OrderDetail> orderDetail = repository.findById("123456");
        Assert.assertNotNull(orderDetail);
    }

    @Test
    public  void findByOrderId(){

        List<OrderDetail> byOrderId = repository.findByOrderId("1");

        Assert.assertNotNull(byOrderId.size());

    }
}