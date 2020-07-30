package com.zhongruan.web.admin;

import com.zhongruan.entity.News;
import com.zhongruan.entity.User;
import com.zhongruan.service.NewsService;
import com.zhongruan.service.TagService;
import com.zhongruan.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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



}
