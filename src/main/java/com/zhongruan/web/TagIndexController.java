package com.zhongruan.web;
import com.zhongruan.entity.Tag;
import com.zhongruan.service.NewsService;
import com.zhongruan.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
/**
 * @Author: Serena
 * @Date: 2020/8/2 22:04
 * @description:
 */

@Controller

public class TagIndexController {
    @Autowired
    private NewsService newService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC)
                               Pageable pageable, @PathVariable Long id , Model model){
        List<Tag> tags = tagService.listTagTop(20);
        if (id ==-1){
            //获得第一个分类的对象并获取Id
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", newService.listNew(id, pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}

