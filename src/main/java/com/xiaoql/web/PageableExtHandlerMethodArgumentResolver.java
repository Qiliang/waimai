//package com.xiaoql.web;
//
//
//import org.springframework.core.MethodParameter;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//public class PageableExtHandlerMethodArgumentResolver extends PageableHandlerMethodArgumentResolver {
//
//    @Override
//    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
//                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
//        Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
//        return new PageRequest(pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
//    }
//}
