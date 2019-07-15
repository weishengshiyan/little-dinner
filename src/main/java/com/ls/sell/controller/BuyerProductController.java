package com.ls.sell.controller;

import com.ls.sell.VO.ProductInfoVO;
import com.ls.sell.VO.ProductVO;
import com.ls.sell.VO.ResultVO;
import com.ls.sell.dataobject.ProductCategory;
import com.ls.sell.dataobject.ProductInfo;
import com.ls.sell.enums.ProductStatusEnum;
import com.ls.sell.service.CategoryService;
import com.ls.sell.service.ProductService;
import com.ls.sell.utils.ResultVOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list() {
        //查询所有的上架的商品
        List<ProductInfo> productInfos = productService.findupAll();
        System.out.println("端口8888执行了一次");
        //查询所有类目
        List<Integer> categoryList = new ArrayList<>();
        //传统方法 //将查到的上架商品的类型同过for循环，添加到list中在查出来
        for (ProductInfo productInfo : productInfos) {
            //获取产品的类TypeId
            categoryList.add(productInfo.getCategoryType());
        }
        //通过Id的list表查出来产品分类信息
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryList);

        //数据整合
        List<ProductVO> productVOList = new ArrayList<>();
        //把查到的产品分类进行遍历
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            //将创建的二级name type 和foods属性个类目信息中的CategoryType，CategoryName 对照
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            //foods属性包含了productInfo中的属性信息
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();

            for (ProductInfo productInfo : productInfos) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {


                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //spring中的工具类，可以直接拷贝有相同属性的数据，
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    //将产品的信息直接放入到类目的foods中
                    productInfoVOList.add(productInfoVO);

                }
            }

            productVO.setProductInfoVOList(productInfoVOList);
            //类目foods中里面产品
            productVOList.add(productVO);
        }


//        //创建一个表头
//        ResultVO resultVO =new ResultVO();
//        //中间部分
//        ProductVO productVO =new ProductVO();
//        //定义前端所需要的数据
//        ProductInfoVO productInfoVO = new ProductInfoVO();
//        //将产品的数据作为一个list放入到中间部分的list中
//        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
//
        ResultVO resultVO = new ResultVO();


//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        //resultVO中放的是类目
//        resultVO.setData(productVOList);


        return ResultVOUtils.success(productVOList);
    }
}
