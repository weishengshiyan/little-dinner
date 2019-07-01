package com.ls.sell.dto;

import com.ls.sell.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private  String orderId;

    private String buyerName;
    /**电话*/
    private String buyerPhone;
    /**地址*/
    private String buyerAddress;
    /**opendid*/
    private String buyerOpenid;
    /**数量*/
    private BigDecimal orderAmount;
    /**订单状态*/
    private Integer orderStatus;
    /**支付状态*/
    private Integer PayStatus;
    /**创建时间*/
    private Date createTime;
    /**更新时间*/
    private  Date updateTime;

    private List<OrderDetail> orderDetailList;
}
