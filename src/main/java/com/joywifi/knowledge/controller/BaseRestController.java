package com.joywifi.knowledge.controller;

import com.joywifi.knowledge.entity.BaseEntity;
import com.joywifi.knowledge.model.AjaxResponse;
import com.joywifi.knowledge.model.ErrorResponse;
import com.joywifi.knowledge.model.SuccessResponse;
import com.joywifi.knowledge.service.BaseService;
import com.joywifi.knowledge.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.web.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Map;

public class BaseRestController<M extends BaseEntity, ID extends Serializable> {

    @Autowired
    private BaseService<M, ID> baseService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<Page<M>> list(Pageable pageable, HttpServletRequest request) {
        Map<String, SearchFilter> filters = SearchFilter.parse(Servlets.getParametersStartingWith(request, Constants.SEARCH_PREFIX));
        Page<M> page = baseService.findPage(filters, pageable);
        return new SuccessResponse<>(page);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse<M> show(@PathVariable("id") ID id) {
        return new SuccessResponse<>(baseService.get(id));
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse<M> create(@Valid M m, BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse<>();
        }

        baseService.save(m);
        return new SuccessResponse<>(m);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse<M> update(@PathVariable("id") ID id, M m, BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorResponse<>(m);
        }

        baseService.update(id, m);
        return new SuccessResponse<>(m);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public AjaxResponse<String> delete(@PathVariable("id") ID id) {
        baseService.delete(id);
        return new SuccessResponse<>("");
    }

    @RequestMapping(value = "batch/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public AjaxResponse<String> deleteInBatch(@RequestParam(value = "ids", required = false) ID[] ids) {
        baseService.delete(ids);
        return new SuccessResponse<>("");
    }
}
