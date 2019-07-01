package com.ls.sell.service.imp;

import com.ls.sell.dataobject.ProductCategory;
import com.ls.sell.repository.ProductCategoryRepository;
import com.ls.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    //findOne出错，返回的类型是以optional<S>类型
    @Override
    public Optional<ProductCategory> findById(Integer category) {
        return repository.findById(category);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
