package com.zhongruan.service;

import com.zhongruan.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    // 全查
    List<Type> listType();

    // 分页全查
    Page<Type> listType(Pageable pageable);

    Type save(Type type);

    Type getTypeByName(String name);

    void delete(long id);

    Type getTypeById(long id);

    Type updateType(long id, Type type);
}
