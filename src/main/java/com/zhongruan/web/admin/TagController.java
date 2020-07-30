package com.zhongruan.web.admin;

import com.zhongruan.entity.Tag;
import com.zhongruan.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.Id;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5, sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    // 新增 跳转到新增页面
    @GetMapping("/tags/input")
    public String toAdd(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags/add")
    public String add(Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag t1 = tagService.getTagByName(tag.getName());
        if (t1 != null) {
            // 当名称重复时  像tag对象中的name属性存放一条 nameError ： 不能添加重复的标签名
            result.rejectValue("name", "nameError", "不能添加重复的标签名");
            return "admin/tags-input";
        }

        // 判断是否保存成功
        Tag t2 = tagService.save(tag);
        if (t2 == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";

    }

    // =============
    // 删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes attributes) {
        tagService.deleteById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

    //==============
    // 编辑
    @GetMapping("/tags/{id}/toUpdate")
    public String toUpdate(@PathVariable long id, Model model) {
        Tag t1 = tagService.getTagById(id);
        model.addAttribute("tag", t1);
        return "admin/tags-input";
    }

    @PostMapping("/tags/update/{id}")
    public String update(Tag tag, BindingResult result, @PathVariable long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "error", "该标签已存在");
            return "admin/tags-input";
        }

        Tag tag2 = tagService.save(tag);
        if (tag2 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";

    }


}
