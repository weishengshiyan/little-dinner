package com.ls.sell.utils;

import com.ls.sell.VO.ResultVO;
import org.aspectj.apache.bcel.classfile.Code;

public class ResultVOUtils {

    public static ResultVO success(Object object) {

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);

        return resultVO;
    }

    public static ResultVO success() {
        return success(null);

    }


    public static ResultVO error(Integer code, String msg) {

        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;


    }


}
