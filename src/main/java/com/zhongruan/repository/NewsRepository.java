package com.zhongruan.repository;

import com.zhongruan.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// JpaSpecificationExecutor  实现条件查询
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor {
    //凡是被推荐都会被找出来
    @Query("select n from News n where n.recommend = true")
    List<News> findTop(Pageable p);

    // ?1 占位符  表示方法的第一个参数      ?2
    //根据文章主题内容查询
    @Query("select n from News n where n.title like ?1 or n.content like ?1")
    Page<News> findByQuery(String query, Pageable p);

}
