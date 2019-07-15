package com.ls.sell.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)//字段为空的时候，直接屏蔽闭掉。
public class ResultVO<T> {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     * 这样写是为了返回空字符，而不是null
     * private String msg="";
     */
    private String msg;
    /**
     * 具体数据
     */
    private T data;
}
