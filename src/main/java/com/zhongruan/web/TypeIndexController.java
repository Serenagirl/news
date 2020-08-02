package com.zhongruan.web;

import com.zhongruan.entity.NewQuery;
import com.zhongruan.entity.Type;
import com.zhongruan.service.NewsService;
import com.zhongruan.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeIndexController {
    @Autowired
    TypeService typeService;

    @Autowired
    NewsService newsService;


    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable long id, Model model) {
        // 根据数据量 将type进行展示
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
            // 得到最大数据量的分类
            id = types.get(0).getId();
        }
        // 查询对象
        NewQuery nq = new NewQuery();
        nq.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", newsService.listNews(pageable, nq));
        model.addAttribute("activeId", id);
        return "types";
    }

    /*
    *  作业
    *       1. 将当前项目完善(分类、 标签)  明天抽查
    *       2. 搜索条件的保存
    * */

}
