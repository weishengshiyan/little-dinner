package com.ls.sell.repository;

import com.ls.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public  void findOneTest(){
        Optional<ProductCategory> byId = repository.findById(1);
        System.out.println(byId.toString());
    }

    @Test
    @Transactional//这里添加，会直接回滚，不会像数据库添加数据
    public  void save(){
        ProductCategory productCategory = new ProductCategory("男生最爱",5);
        ProductCategory result=repository.save(productCategory);
        //断电不能为空，出现的值为result
        Assert.assertNotNull(result);
        //assert断点，第一个值填不希望出现的值，第二个添期望值
        Assert.assertNotEquals(null,result);

    }

    @Test
   public void   findByCategoryTypeIn(){
        //传入的是一个list列表。
        List<Integer> list = Arrays.asList(1,3,6);
        List<ProductCategory> result=repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
   }

}