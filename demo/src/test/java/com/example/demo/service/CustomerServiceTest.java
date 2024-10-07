package com.example.demo.service;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCustomers_ReturnsAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("John");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Jane");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.getCustomers();

        assertEquals(2, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
        assertEquals("Jane", customers.get(1).getFirstName());
    }

    @Test
    void getCustomerById_WhenExists_ReturnsCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.getCustomerById(1L);

        assertEquals("John", foundCustomer.getFirstName());
    }

    @Test
    void getCustomerById_WhenNotExists_ThrowsException() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void createCustomer_ReturnsCreatedCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("New Customer");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        assertEquals("New Customer", createdCustomer.getFirstName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void updateCustomer_WhenExists_ReturnsUpdatedCustomer() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setFirstName("Old Customer");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("Updated Customer");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        assertEquals("Updated Customer", result.getFirstName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void updateCustomer_WhenNotExists_ThrowsException() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setFirstName("Updated Customer");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(1L, updatedCustomer));
    }

    @Test
    void patchCustomer_WhenExists_ReturnsPatchedCustomer() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setFirstName("Old Customer");
        existingCustomer.setEmailOffice("oldoffice@example.com");

        Customer patchCustomer = new Customer();
        patchCustomer.setFirstName("Patched Customer");
        patchCustomer.setEmailOffice("patchedoffice@example.com");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        Customer result = customerService.patchCustomer(1L, patchCustomer);

        assertEquals("Patched Customer", result.getFirstName());
        assertEquals("patchedoffice@example.com", result.getEmailOffice());
    }

    @Test
    void patchCustomer_WhenNotExists_ThrowsException() {
        Customer patchCustomer = new Customer();
        patchCustomer.setFirstName("Patched Customer");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.patchCustomer(1L, patchCustomer));
    }

    @Test
    void deleteCustomer_WhenExists_ReturnsTrue() {
        Long customerId = 1L;

        when(customerRepository.existsById(customerId)).thenReturn(true);

        boolean result = customerService.deleteCustomer(customerId);

        assertTrue(result);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void deleteCustomer_WhenNotExists_ThrowsException() {
        Long customerId = 1L;

        when(customerRepository.existsById(customerId)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(customerId));
        verify(customerRepository, never()).deleteById(customerId);
    }
}
