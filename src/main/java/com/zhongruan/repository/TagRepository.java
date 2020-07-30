package com.zhongruan.repository;

import com.zhongruan.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag getTagByName(String name);

}
