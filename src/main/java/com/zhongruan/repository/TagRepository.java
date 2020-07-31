package com.zhongruan.repository;

import com.zhongruan.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag getTagByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

}
