package com.zhongruan.service.impl;

import com.zhongruan.entity.News;
import com.zhongruan.repository.NewsRepository;
import com.zhongruan.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NewServiceImpl implements NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Override
    public Page<News> listNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public News save(News news) {
        // 指定创建时间
        news.setCreateTime(new Date());
        news.setUpdateTime(new Date());
        news.setViews(0);
        return newsRepository.save(news);
    }

    @Override
    public News updateNews(Long id, News news) {
        News n = newsRepository.getOne(id);
        if (n == null) {
            System.out.println("不存在该新闻");
            return null;
        }
        news.setUpdateTime(new Date());
        return newsRepository.save(news);
    }

}
