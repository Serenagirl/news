package com.zhongruan.service;

import com.zhongruan.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    // 分页全查
    Page<Tag> listTag(Pageable p);

    // 全查
    List<Tag> listTag();

    // 根据字符串id 查找对应的 集合
    List<Tag> listTag(String ids);

    Tag getTagByName(String name);

    Tag save(Tag t);

    void deleteById(long id);

    Tag getTagById(long id);

}
