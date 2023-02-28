package com.cg.controller;


import com.cg.model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @GetMapping
    public String showListPage(Model model) {

        List<Customer> customers = initCustomers();
        model.addAttribute("customers", customers);

        return "customer/list";
    }


    public List<Customer> initCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "NVA", "nva@co.cc", "2345", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
        customers.add(new Customer(2L, "NVB", "nvb@co.cc", "3456", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
        customers.add(new Customer(3L, "NVC", "nvc@co.cc", "4567", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
        customers.add(new Customer(4L, "NVD", "nvd@co.cc", "5678", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
        customers.add(new Customer(5L, "NVE", "nve@co.cc", "6789", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
        customers.add(new Customer(6L, "NVF", "nvf@co.cc", "78910", "28 Nguyễn Tri Phương", BigDecimal.ZERO));


        return customers;
    }
}
