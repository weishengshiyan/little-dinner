package com.ls.sell.repository;

import com.ls.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;



@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID="123456";
    @Test
    public  void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234");
        orderMaster.setBuyerName("师弟");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerAddress("河北");
        orderMaster.setBuyerPhone("123345678993");
        orderMaster.setOrderAmount(new BigDecimal(6.3));
        OrderMaster save = repository.save(orderMaster);
        Assert.assertNotNull(save);

    }

    @Test
    public void findById(){

        Optional<OrderMaster> byId = repository.findById("1");
        Assert.assertNotNull(byId);

    }
    @Test
  public  void  findByBuyerOpenid() throws Exception{
        PageRequest request=PageRequest.of(1,3);

        Page<OrderMaster> byBuyerOpenid = repository.findByBuyerOpenid(OPENID,request);

        System.out.println(byBuyerOpenid.getTotalElements());
        Assert.assertNotNull(byBuyerOpenid.getTotalElements());
    }
}