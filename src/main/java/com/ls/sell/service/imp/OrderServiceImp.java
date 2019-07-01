package com.ls.sell.service.imp;

import com.ls.sell.dataobject.OrderDetail;
import com.ls.sell.dataobject.OrderMaster;
import com.ls.sell.dataobject.ProductInfo;
import com.ls.sell.dto.CartDTO;
import com.ls.sell.dto.OrderDTO;
import com.ls.sell.enums.OrderStatusEnum;
import com.ls.sell.enums.PayStatusEnum;
import com.ls.sell.enums.ResultEnum;
import com.ls.sell.exception.SellException;
import com.ls.sell.repository.OrderDetailRepository;
import com.ls.sell.repository.OrderMasterRepository;
import com.ls.sell.service.OrderService;
import com.ls.sell.service.ProductService;
import com.ls.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional//如果抛出异常会进行回滚
public class OrderServiceImp implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

//        List<CartDTO> cartDTOList=new ArrayList<>();
        //查询商品（数量 单价）

        for(OrderDetail orderDetail: orderDTO.getOrderDetailList() ){
            Optional<ProductInfo> productInfo= productService.findOne(orderDetail.getProductId());

            if(productInfo.get()==null){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算总价
            orderAmount= productInfo.get().getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);


//            CartDTO cartDTO=new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//             cartDTOList.add(cartDTO);
        }

        //写入订单数据库

        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //扣库存
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e->
                            new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

            return orderDTO;



    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
