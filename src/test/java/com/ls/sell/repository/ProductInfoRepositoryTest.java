package com.ls.sell.repository;


import com.ls.sell.dataobject.ProductInfo;
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
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void SaveTest(){
        ProductInfo productInfo = new ProductInfo();
       productInfo.setProductId("123456");
        productInfo.setProductName("方便面");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃都知道");
        productInfo.setProductIcon("http://xxxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(6);

        ProductInfo save = repository.save(productInfo);
        Assert.assertNotEquals(null,save);

    }


    @Test
    public void findById(){
        Optional<ProductInfo> byId = repository.findById("1");
        System.out.println(byId.toString());

    }

    @Test
    public  void findByProductSatus() throws  Exception{
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
                Assert.assertNotEquals(0,byProductStatus.size());
    }

}