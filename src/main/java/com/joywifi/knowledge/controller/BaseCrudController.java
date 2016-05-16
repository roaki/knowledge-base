package com.joywifi.knowledge.controller;

import com.joywifi.knowledge.annotation.CheckToken;
import com.joywifi.knowledge.entity.BaseEntity;
import com.joywifi.knowledge.service.BaseService;
import com.joywifi.knowledge.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.web.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Map;

public class BaseCrudController<M extends BaseEntity, ID extends Serializable> extends BaseController<M> {

    @Autowired
    private BaseService<M, ID> baseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Pageable pageable, HttpServletRequest request, Model model) {
        Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<M> page = baseService.findPage(filters, pageable);
        model.addAttribute("page", page);
        beforeList(model);
        return viewName("list");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") ID id, Model model) {
        M m = baseService.get(id);
        model.addAttribute(entityName(), m);
        beforeShow(model);
        return viewName("show");
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String _new(Model model) {
        beforeNew(model);
        return viewName("new");
    }

    @CheckToken
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid M m, BindingResult result, RedirectAttributes redirectAttributes) {
        if (hasError(m, result)) {
            return viewName("new");
        }
        baseService.save(m);
        if (StringUtils.isNotBlank(flashCreateMessage)) {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, flashCreateMessage);
        } else {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "新增成功");
        }
        return redirectToUrl("");
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") ID id, Model model) {
        M m = baseService.get(id);
        model.addAttribute(entityName(), m);
        beforeEdit(model);
        return viewName("edit");
    }

    @CheckToken
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") ID id, M m, BindingResult result, RedirectAttributes redirectAttributes) {
        if (hasError(m, result)) {
            return viewName("edit");
        }

        baseService.update(id, m);
        if (StringUtils.isNotBlank(flashUpdateMessage)) {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, flashCreateMessage);
        } else {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "更新成功");
        }
        return redirectToUrl("");
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") ID id,
                         RedirectAttributes redirectAttributes, HttpServletRequest request) {

        baseService.delete(id);

        if (StringUtils.isNotBlank(flashDeleteMessage)) {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, flashCreateMessage);
        } else {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
        }
        redirectAttributes.addAllAttributes(Servlets.getParametersStartingWith(request, ""));
        return redirectToUrl("");
    }

    @RequestMapping(value = "batch/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteInBatch(@RequestParam(value = "ids", required = false) ID[] ids,
                                RedirectAttributes redirectAttributes, HttpServletRequest request) {
        baseService.delete(ids);

        if (StringUtils.isNotBlank(flashDeleteMessage)) {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, flashCreateMessage);
        } else {
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, "删除成功");
        }
        redirectAttributes.addAllAttributes(Servlets.getParametersStartingWith(request, ""));
        return redirectToUrl("");
    }
}