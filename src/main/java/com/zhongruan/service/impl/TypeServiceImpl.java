package com.zhongruan.service.impl;

import com.zhongruan.entity.Type;
import com.zhongruan.repository.TypeRepository;
import com.zhongruan.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.lang.model.util.Types;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeRepository typeRepository;

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public Type save(Type type) {
        // save 返回保存过的Type对象
        return typeRepository.save(type);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public void delete(long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeById(long id) {
        return typeRepository.getOne(id);
    }

    @Override
    public Type updateType(long id, Type type) {
        Type t = typeRepository.getOne(id);
        if(t == null) {
            System.out.println("没有该分类");
            return null;
        }
        return typeRepository.save(type);
    }

}
