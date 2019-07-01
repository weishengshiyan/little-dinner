package com.ls.sell.dataobject;

import com.ls.sell.enums.OrderStatusEnum;
import com.ls.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
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
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();
    /**支付状态*/
    private Integer PayStatus= PayStatusEnum.WAIT.getCode();
    /**创建时间*/
    private Date createTime;
    /**更新时间*/
    private  Date updateTime;

}
