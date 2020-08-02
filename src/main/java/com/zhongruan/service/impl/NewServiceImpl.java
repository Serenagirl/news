package com.zhongruan.service.impl;

import com.zhongruan.entity.NewQuery;
import com.zhongruan.entity.News;
import com.zhongruan.repository.NewsRepository;
import com.zhongruan.service.NewsService;
import com.zhongruan.utils.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NewServiceImpl implements NewsService {
    @Autowired
    NewsRepository newsRepository;

    @Override
    public Page<News> listNews(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public Page<News> listNews(Pageable pageable, NewQuery newQuery) {
        return newsRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                // root                 数据库中的字段名
                // CriteriaBuilder      构建查询条件
                // CriteriaQuery        执行查询条件

                // 1. 创建集合 存储查询条件
                List<Predicate> list = new ArrayList<>();
                // 2. 添加查询条件
                if (!"".equals(newQuery.getTitle()) && newQuery.getTitle() != null) {
                    // 当title 有值时  项目list集合中存储查询条件     select * from t_user where name like %zhang%
                    list.add(cb.like(root.<String>get("title"), "%" + newQuery.getTitle() + "%"));
                }
                if (newQuery.getTypeId() != null) {
                    list.add(cb.equal(root.<Long>get("type").get("id"), newQuery.getTypeId()));
                }
                if (newQuery.isRecommend()) {
                    list.add(cb.equal(root.<Boolean>get("recommend"), newQuery.isRecommend()));
                }
                // 3. 执行查询
                cq.where(list.toArray(new Predicate[list.size()]));
                return null;
            }
        },pageable);
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

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.getOne(id);
    }

    @Override
    public List<News> listRecommendNewsTop(int i) {
        // 排序对象
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        // 分类对象
        Pageable pageable = PageRequest.of(0, i, sort);
        return newsRepository.findTop(pageable);
    }

    @Override
    public Page<News> listNews(String query, Pageable pageable) {
        return newsRepository.findByQuery(query, pageable);
    }

    //新闻管理中的新闻列表（包含了查询）分页数据的展示
    @Override
    public Page<News> listNew(Pageable pageable, NewQuery newQuery) {
        return newsRepository.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                //叠加判断
                if (!"".equals(newQuery.getTitle())&&newQuery.getTitle()!=null){
                    //根据标题进行模糊查询
                    predicates.add(cb.like(root.<String>get("title"),"%"+newQuery.getTitle()+"%"));
                }
                if (newQuery.getTypeId()!=null){//逗号前那部分指定查找的对象，后面指前面所查的东西返回的类型和值
                    predicates.add(cb.equal(root.get("type").get("id"), newQuery.getTypeId()));
                }
                if (newQuery.isRecommend()){
                    predicates.add(cb.equal(root.get("recommend"), newQuery.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public News saveNew(News news) {
        if (news.getId()==null){
            news.setCreateTime(new Date());
            news.setUpdateTime(new Date());
        }
        return newsRepository.save(news);
    }


    @Override
    //根据id获取
    public News getNew(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public News updateNew(Long id, News news) {
        News news1 = newsRepository.findById(id).orElse(null);
        if (news1==null){
            System.out.println("未获得更新对象");
        }
        BeanUtils.copyProperties(news, news1);
        news1.setUpdateTime(new Date());
        return newsRepository.save(news1);

    }

    @Override
    public void deleteNew(Long id) {
        newsRepository.deleteById(id);
    }

    //主页显示新闻列表
    @Override
    public Page<News> listNew(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public List<News> listRecommendNewTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return newsRepository.findTop(pageable);
    }

    @Override
    public Page<News> listNew(String query, Pageable pageable) {
        return newsRepository.findByQuery(query, pageable);
    }

    @Override
    public News getAndConvert(Long id) {
        News news = newsRepository.findById(id).orElse(null);
        if (news==null){
            System.out.println("该新闻不存在");
        }
        News news1 = new News();
        BeanUtils.copyProperties(news, news1);
        String content = news1.getContent();
        //展示出来的新闻就是html的样式了
        news1.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return news1;
    }

    @Override
    public Page<News> listNew(Long tagId, Pageable pageable) {

        return newsRepository.findAll(new Specification<News>(){

            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //关联查询
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        },pageable);
    }

}
