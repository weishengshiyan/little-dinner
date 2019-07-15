package com.ls.sell.controller;


import com.ls.sell.VO.ResultVO;
import com.ls.sell.converter.OrderForm2OrderDTOConverter;
import com.ls.sell.dto.OrderDTO;
import com.ls.sell.enums.ResultEnum;
import com.ls.sell.exception.SellException;
import com.ls.sell.form.OrderForm;
import com.ls.sell.service.BuyerService;
import com.ls.sell.service.OrderService;
import com.ls.sell.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单

    @PostMapping("/create")
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

    @GetMapping("/list")                  //@RequestParam进行参数校验，加入defaultValue为参数默认默认值
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        //加入判断,微信号是否为空，如果微信号为空 ，爆出参数错误。
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】opendid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }


        //原来的pageRequest无法new出来，应该是改成了单例
        PageRequest request = PageRequest.of(page, size);
        //通过openid查询数据，返回的对象是orderDTO ，传入的数据是openid和分页数据
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, request);

        return ResultVOUtils.success(orderDTOPage.getContent());

    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtils.success(orderDTO);

    }

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {

        buyerService.cancelOrder(openid, orderId);


        return ResultVOUtils.success();


    }


}
