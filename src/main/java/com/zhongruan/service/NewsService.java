package com.zhongruan.service;

import com.zhongruan.entity.NewQuery;
import com.zhongruan.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {
    // 分页的全查
    Page<News> listNews(Pageable pageable);

    // 带查询条件的分页全查
    Page<News> listNews(Pageable pageable, NewQuery newQuery);

    // 保存
    News save(News news);

    // 更新
    News updateNews(Long id, News news);

    // 删除
    void deleteById(Long id);

    // 根据id查找news
    News getNewsById(Long id);

    //删除接口 传个id就行了
    void deleteNew(Long id);

    //主页显示新闻列表
    Page<News> listNew(Pageable pageable);

    //主页推荐最新新闻列表
    List<News> listRecommendNewTop(Integer size);

    //全局搜索
    Page<News> listNew(String query,Pageable pageable);

    //处理Mrakdown
    News getAndConvert(Long id);

}
