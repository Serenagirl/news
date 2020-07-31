package com.zhongruan.service.impl;

import com.zhongruan.entity.Type;
import com.zhongruan.repository.TagRepository;
import com.zhongruan.repository.TypeRepository;
import com.zhongruan.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<Type> listTypeTop(int size) {
        // 需求 查询新闻数量前6的分类
        // 分页  查第一页数据  排序条件根据 list集合的大小  每页的数量为6   降序排序
        Sort sort = Sort.by(Sort.Direction.DESC, "news.size");
        Pageable p = PageRequest.of(0, size, sort);
        return typeRepository.findTop(p);
    }

}
