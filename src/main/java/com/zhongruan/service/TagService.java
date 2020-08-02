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

    // 8.2
    // 查找数据量前n的标签
    List<Tag> listTagTop(int i);

    //获取标签
    Tag getTag(Long id);
    //更新标签 后保存
    Tag updateTag(Long id,Tag tag);


}
