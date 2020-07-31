package com.zhongruan.web.admin;

import com.zhongruan.entity.Type;
import com.zhongruan.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    TypeService typeService;

    @GetMapping("/types")//根据id来进行分类排序 使用降序 只是定义数据显示的方式 DESC后面是传入对象
    public String types(Model model, @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        // model.addAttribute("list", typeService.listType());
        Page<Type> page = typeService.listType(pageable);
        model.addAttribute("page", page);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /*
    未校验
    @PostMapping("/types/add")
    public String post(Type type, RedirectAttributes attributes) {
        Type t = typeService.save(type);
        System.out.println(t);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }*/

    // 后端的校验 判断是否存在重复分类 添加操作
    @PostMapping("/types/add")
    public String post(Type type, BindingResult result, RedirectAttributes attributes) {
        // 查找是否有同名的类别
        Type t1 = typeService.getTypeByName(type.getName());
        if (t1 != null) {
            // attributes.addFlashAttribute("message", "新增类别重名");
            result.rejectValue("name", "nameError", "不能添加重复的类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        // 没有错误 则继续执行添加操作
        Type t = typeService.save(type);
        System.out.println(t);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    // 7.30
    // 删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes attributes) {
        typeService.delete(id);
        attributes.addFlashAttribute("message", "删除成功");
        // 删除之后  再次全查
        return "redirect:/admin/types";
    }

    // 更新的前置操作
    @GetMapping("/types/{id}/toUpdate")
    public String toUpdate(@PathVariable long id, Model model) {
        // 找到需要修改的类别对象
        Type t1 = typeService.getTypeById(id);
        model.addAttribute("type", t1);
        return "admin/types-input";
    }

    // 更新
    @PostMapping("/types/update/{id}")
    public String update(@RequestParam long id, Type type, BindingResult result, RedirectAttributes attributes) {
        // 修改后名称是否存在
        Type t1 = typeService.getTypeByName(type.getName());
        if (t1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t2 = typeService.updateType(id, type);
        if (t2 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

}
