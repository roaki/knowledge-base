package com.joywifi.knowledge.controller;

import com.joywifi.knowledge.entity.Role;
import com.joywifi.knowledge.service.RoleService;
import com.joywifi.knowledge.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.web.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<Role> roles = roleService.findPage(filters, pageable);
        model.addAttribute("roles", roles);

        return "role/list";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") String id, Model model) {
        Role role = roleService.get(id);
        model.addAttribute("role", role);
        return "role/show";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new() {
        return "role/new";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "role/new";
        }

        role.setStatus(Constants.ROLE_STATUS_ENABLE);
        roleService.save(role);

        redirectAttributes.addFlashAttribute("msg", "新增角色成功");
        return "redirect:/role/";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model model) {
        Role role = roleService.get(id);
        model.addAttribute("role", role);

        return "role/edit";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") String id, Role role, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "role/edit";
        }

        roleService.update(id, role);

        redirectAttributes.addFlashAttribute("msg", "更新角色成功");
        return "redirect:/role/";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id,
                         RedirectAttributes redirectAttributes, HttpServletRequest request) {

        roleService.delete(id);

        redirectAttributes.addFlashAttribute("msg", "删除角色成功");
        redirectAttributes.addAllAttributes(Servlets.getParametersStartingWith(request, ""));
        return "redirect:/role/";
    }
}
