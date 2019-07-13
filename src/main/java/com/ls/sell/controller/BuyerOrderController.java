package com.ls.sell.controller;


import com.ls.sell.VO.ResultVO;
import com.ls.sell.converter.OrderForm2OrderDTOConverter;
import com.ls.sell.dto.OrderDTO;
import com.ls.sell.enums.ResultEnum;
import com.ls.sell.exception.SellException;
import com.ls.sell.form.OrderForm;
import com.ls.sell.service.OrderService;
import com.ls.sell.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    //创建订单

    @PostMapping ("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
// BindingResult 是紧跟在@Validated后面一起用的，
// bindingResult.hasErrors()是为了验证@Validated后面bean 里
// 是否有不符合注解条件的错误信息
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //转换器将orderFrom转换成orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtils.success(map);

    }

}
