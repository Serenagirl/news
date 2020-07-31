package com.zhongruan.web.admin;

import com.zhongruan.entity.NewQuery;
import com.zhongruan.entity.News;
import com.zhongruan.entity.User;
import com.zhongruan.service.NewsService;
import com.zhongruan.service.TagService;
import com.zhongruan.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class NewController {
    @Autowired
    TypeService typeService;

    @Autowired
    NewsService newsService;

    @Autowired
    TagService tagService;


    // 分页全查
    @GetMapping("/news")
    public String news(@PageableDefault(size = 3) Pageable pageable, Model model) {
        // 不仅需要传递当前页的数据  还需要传递 所有的类别
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", newsService.listNews(pageable));
        return "admin/news";
    }

    // 新增的前置操作
    @GetMapping("/news/input")
    public String toAdd(Model model) {
        // 传递一个空的New对象   所有类别  所有标签
        model.addAttribute("news", new News());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return "admin/news-input";
    }

    // 新增/编辑
    @PostMapping("/news/add")
    public String addOrUpdate(News news, HttpSession session, RedirectAttributes attributes) {
        // 得到用户对象  将用户对象存储news对象中
        news.setUser((User) session.getAttribute("user"));
        // 得到Type对象  将Type对象存储到News对象中   原因是前台穿的Type对象  只有id值
        news.setType(typeService.getTypeById(news.getType().getId()));
        // 得到Tags对象  TagIds 前台页面封装过的 id字符串
        news.setTags(tagService.listTag(news.getTagIds()));
        // System.out.println(news.getTagIds());

        News n;
        // 判断 当前是 保存还是 更新
        if (news.getId() == null) {
            n = newsService.save(news);
        } else {
            n = newsService.updateNews(news.getId(), news);
        }

        if (n == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/news";
    }

    //删除
    @GetMapping("/news/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        newsService.deleteById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/news";
    }

    // 编辑
    @GetMapping("news/{id}/toUpdate")
    public String toUpdate(@PathVariable Long id, Model model) {
        News news = newsService.getNewsById(id);
        news.init();   // tag的list集合 转换成了 对应的字符串
        model.addAttribute("news", news);
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return "admin/news-input";
    }


    // 搜索
    // 1. 搜索之后 搜索条件应当保留
    // 2. 搜索之后的翻页 搜索条件也应当保留

    // 结论 使用 ajax 实现局部刷新功能
    @PostMapping("/news/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model,
                         NewQuery newQuery) {
        model.addAttribute("page", newsService.listNews(pageable, newQuery));
      /*  System.out.println(newsService.listNews(pageable, newQuery).getContent());*/
        // 局部刷新页面
        return "admin/news :: newsList";
    }
}
