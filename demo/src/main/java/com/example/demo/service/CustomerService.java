package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> getCustomers(){
        logger.info("Fetching all customers");
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id){
        logger.info("Fetching customer with ID: {}", id);
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found."));
    }

    @Transactional
    public Customer createCustomer(Customer customer){
        logger.info("Creating new customer: {}", customer.toString());
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer customer){
        logger.info("Updating customer with ID: {}", id);
        customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found."));
        customer.setId(id);
        return customerRepository.save(customer);

    }

    @Transactional
    public Customer patchCustomer(Long id, Customer customer) {
        logger.info("Partially updating customer with ID: {}", id);
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found."));

        if (customer.getFirstName() != null) {
            existingCustomer.setFirstName(customer.getFirstName());
        }
        if (customer.getLastName() != null) {
            existingCustomer.setLastName(customer.getLastName());
        }
        if (customer.getEmailOffice() != null) {
            existingCustomer.setEmailOffice(customer.getEmailOffice());
        }
        if (customer.getEmailPersonal() != null) {
            existingCustomer.setEmailPersonal(customer.getEmailPersonal());
        }
        if (customer.getPhone() != null) {
            existingCustomer.setPhone(customer.getPhone());
        }
        if (customer.getFamilyMembers() != null) {
            existingCustomer.setFamilyMembers(customer.getFamilyMembers());
        }

        return customerRepository.save(existingCustomer);
    }

    @Transactional
    public boolean deleteCustomer(Long id){
        logger.info("Deleting customer with ID: {}", id);
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
    }
}
