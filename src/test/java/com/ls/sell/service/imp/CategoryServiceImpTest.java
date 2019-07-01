package com.ls.sell.service.imp;

import com.ls.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImpTest {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @Test
    public void findById() {
        Optional<ProductCategory> byId = categoryServiceImp.findById(1);

        Assert.assertEquals(new Integer(1),byId.get().getCategoryId());

    }

    @Test
    public void findAll() {

        List<ProductCategory> all = categoryServiceImp.findAll();
        Assert.assertNotEquals(0,all.size());


    }

    @Test
    public void findByCategoryTypeIn() throws Exception{

        List<ProductCategory> byCategoryTypeIn = categoryServiceImp.findByCategoryTypeIn(Arrays.asList(1,3,6));
        Assert.assertNotEquals(0,byCategoryTypeIn.size());

    }

    @Test
    public void save() {

        ProductCategory p = new ProductCategory("所有人都可以", 7);
        ProductCategory save = categoryServiceImp.save(p);
            Assert.assertNotEquals(null,save);
    }
}