package com.cg.controller;


import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IDepositService depositService;

//    private static Long id = 1L;
//
//    private static List<Customer> customers = new ArrayList<>();
//
//    static {
//        customers.add(new Customer(id++, "NVA", "nva@co.cc", "2345", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
//        customers.add(new Customer(id++, "NVB", "nvb@co.cc", "3456", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
//        customers.add(new Customer(id++, "NVC", "nvc@co.cc", "4567", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
//        customers.add(new Customer(id++, "NVD", "nvd@co.cc", "5678", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
//        customers.add(new Customer(id++, "NVE", "nve@co.cc", "6789", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
//        customers.add(new Customer(id++, "NVF", "nvf@co.cc", "78910", "28 Nguyễn Tri Phương", BigDecimal.ZERO));
//    }


    @GetMapping
    public String showListPage(Model model) {

        List<Customer> customers = customerService.findAll();

        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @GetMapping("/update/{customerId}")
    public String showUpdatePage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        return "customer/edit";
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchKey") String searchKey, Model model) {

        searchKey = "%" + searchKey + "%";

        List<Customer> customers = customerService.findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(searchKey, searchKey, searchKey, searchKey);

        model.addAttribute("customers", customers);

        return "customer/list";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        model.addAttribute("customer", customerOptional.get());

        Deposit deposit = new Deposit();
        deposit.setCustomer(customerOptional.get());

        model.addAttribute("deposit", deposit);

        return "customer/deposit";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Customer customer) {

        customer.setId(null);
        customer.setBalance(BigDecimal.ZERO);
        customerService.save(customer);

        return "customer/create";
    }


    @PostMapping("/update/{customerId}")
    public String update(@PathVariable Long customerId, @ModelAttribute Customer customer) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        customer.setBalance(customerOptional.get().getBalance());
        customer.setId(customerId);
        customerService.save(customer);

        return "redirect:/customers";
    }

    @PostMapping("/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            return "error/404";
        }

        Customer customer = customerOptional.get();
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        BigDecimal newBalance = currentBalance.add(transactionAmount);

        customer.setBalance(newBalance);

        deposit.setCustomer(customer);

        customerService.save(customer);
        depositService.save(deposit);

        deposit.setTransactionAmount(BigDecimal.ZERO);

        model.addAttribute("deposit", deposit);

        return "customer/deposit";
    }

}
