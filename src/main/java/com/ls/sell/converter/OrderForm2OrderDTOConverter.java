package com.ls.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ls.sell.dataobject.OrderDetail;
import com.ls.sell.dto.OrderDTO;
import com.ls.sell.enums.ResultEnum;
import com.ls.sell.exception.SellException;
import com.ls.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();

        System.out.println("llllllllllllllllllllllllllllll" + orderForm);
        //todo
        //在进行字符转字符串
        //pom.xml文件中出错，加入了gson依赖但是检测不到。
        try {
            //将orderForm的getItems中数据转换成一个新的类型List<OrderDetail>
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误，string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        System.out.println("3333333333333333333333333333" + orderDTO);
        return orderDTO;
    }
}
