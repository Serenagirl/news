package com.zhongruan.service;

import com.zhongruan.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    //利用SpringDate提供的分页接口Page<> 指定分页的对象为Pageable
    //Pageable对象是SpringDate库当中定义的一个接口 可以进行分页查询和数据 根据里面的参数进行数据查询并显示
    // 全查
    List<Type> listType();

    // 分页全查
    Page<Type> listType(Pageable pageable);

    Type save(Type type);

    //查找数据
    Type getTypeByName(String name);

    void delete(long id);

    Type getTypeById(long id);

    Type updateType(long id, Type type);

    // 7.31 分类的排序
    List<Type> listTypeTop(int size);

}
