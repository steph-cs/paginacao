package com.example.demo.controller;

import com.example.demo.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService service;

    @GetMapping("/search")
    public ModelAndView search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        
        Page<Customer> users = service.search(searchTerm, page, size);
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/listUsers");
        mv.addObject("users", users);
        mv.addObject("searchTerm", searchTerm);
        
        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            mv.addObject("pageNumbers",pageNumbers);
        }
        return mv;
        

    }

    
    @GetMapping
    public ModelAndView getAll(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        Page<Customer> users = service.findAll(page, size);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/listUsers");
        mv.addObject("users", users);
        
        int totalPages = users.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            mv.addObject("pageNumbers",pageNumbers);
        }
        return mv;
    }

}