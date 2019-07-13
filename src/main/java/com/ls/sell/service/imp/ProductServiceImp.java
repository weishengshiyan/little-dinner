package com.ls.sell.service.imp;

import com.ls.sell.dataobject.ProductInfo;
import com.ls.sell.dto.CartDTO;
import com.ls.sell.enums.ProductStatusEnum;
import com.ls.sell.enums.ResultEnum;
import com.ls.sell.exception.SellException;
import com.ls.sell.repository.ProductInfoRepository;
import com.ls.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductInfoRepository reponsitory;
    @Override
    public Optional<ProductInfo> findOne(String productId) {

        Optional<ProductInfo> productInfo = reponsitory.findById(productId);

        return productInfo;
    }

    @Override
    public List<ProductInfo> findupAll() {
        return reponsitory.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return reponsitory.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return reponsitory.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO: cartDTOList){
            Optional<ProductInfo> productInfo= reponsitory.findById(cartDTO.getProductId());
            if (productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

            }
            Integer result =productInfo.get().getProductStock()+cartDTO.getProductQuantity();
            productInfo.get().setProductStock(result);
            reponsitory.save(productInfo.get());
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for(CartDTO cartDTO: cartDTOList){
            Optional<ProductInfo> productInfo = reponsitory.findById(cartDTO.getProductId());
            if(productInfo.get()==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result=productInfo.get().getProductStock()-cartDTO.getProductQuantity();
                if(result<0){
                    throw  new SellException(ResultEnum.PRODUCT_NOT_ERROR);

                }
                productInfo.get().setProductStock(result);

            reponsitory.save(productInfo.get());
        }
    }


}
