package com.ls.sell.service;

import com.ls.sell.dataobject.ProductCategory;


import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<ProductCategory> findById(Integer category);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
