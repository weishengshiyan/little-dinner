package com.ls.sell.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    @Id//配置主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//配置自增
    private Integer categoryId;

//    private  Date updateTime;

    private  String categoryName;

    private Integer categoryType;

//    private Date createTime;

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
                '}';
    }
    public ProductCategory(){}

public  ProductCategory(String categoryName,Integer categoryType){
        this.categoryName=categoryName;
        this.categoryType=categoryType;

}


}
