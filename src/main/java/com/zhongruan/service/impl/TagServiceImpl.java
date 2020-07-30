package com.zhongruan.service.impl;

import com.zhongruan.entity.Tag;
import com.zhongruan.repository.TagRepository;
import com.zhongruan.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public Page<Tag> listTag(Pageable p) {
        return tagRepository.findAll(p);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        // 1,2,3
        return tagRepository.findAllById(idsToList(ids));
    }
    // 将字符串 转换成 对应的集合
    List<Long> idsToList(String ids) {
        // 定义一个空集合
        List<Long> list = new ArrayList<>();

        if (!"".equals(ids) && ids != null) {
            // split() 字符串的切割方法   1,2,3(字符串) ==> ([1,2,3]数组)
            String[] arr = ids.split(",");
            for (String s : arr) {
                list.add(new Long(s));
            }
        }
        return list;
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.getTagByName(name);
    }

    @Override
    public Tag save(Tag t) {
        return tagRepository.save(t);
    }

    @Override
    public void deleteById(long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTagById(long id) {
        return tagRepository.getOne(id);
    }

}
