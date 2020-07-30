package com.zhongruan.service;

import com.zhongruan.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    // 分页的全查
    Page<News> listNews(Pageable pageable);

    // 保存
    News save(News news);

    // 更新
    News updateNews(Long id, News news);

}
