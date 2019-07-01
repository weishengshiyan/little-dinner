package com.ls.sell.service.imp;

import com.ls.sell.dataobject.ProductInfo;
import com.ls.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImpTest {

    @Autowired
    private ProductServiceImp productServiceImp;

    @Test
    public void findOne() {
        Optional<ProductInfo> productInfo =productServiceImp.findOne("123456");
        Assert.assertEquals("123456",productInfo.get().getProductId() );
        System.out.println(productInfo.toString());
    }



    @Test
    public void findupAll() {

        List<ProductInfo> productInfos = productServiceImp.findupAll();
        Assert.assertNotEquals(0,productInfos.size());
        //不等于0，有size就通过

    }

    @Test
    public  void finAll(){
        //灵魂，设置分页查询的限制，后期可以改写成动态的。
        PageRequest request =PageRequest.of(0,2);
        //分页查询
        Page<ProductInfo> all = productServiceImp.findAll(request);
           // System.out.println(all.getTotalElements());//最后的结果为几个
        //查出的结果不为0，结果通过。后面的期望值，只要填的差不多就行。
        Assert.assertNotEquals(0,all.getTotalElements());
    }

    @Test
    public void save() throws  Exception{

        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(12);

        ProductInfo save = productServiceImp.save(productInfo);
            Assert.assertNotNull(save);
    }


}