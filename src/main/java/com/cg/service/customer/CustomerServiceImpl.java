package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName, String email, String phone, String address) {
        return customerRepository.findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(fullName, email, phone, address);
    }

    @Override
    public List<Customer> findAllByIdNot(Long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Deposit deposit(Deposit deposit) {
        depositRepository.save(deposit);
        customerRepository.save(deposit.getCustomer());

        return deposit;
    }

    @Override
    public Transfer transfer(Transfer transfer) {
        customerRepository.save(transfer.getSender());
        customerRepository.save(transfer.getRecipient());
        transferRepository.save(transfer);

        return transfer;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
