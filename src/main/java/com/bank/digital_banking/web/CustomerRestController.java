package com.bank.digital_banking.web;


import com.bank.digital_banking.Exceptions.CustomerNotFoundException;
import com.bank.digital_banking.dtos.CustomerDto;
import com.bank.digital_banking.entities.Customer;
import com.bank.digital_banking.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {

    private BankAccountService bankAccountService;

@GetMapping("/customers")
    public List<CustomerDto> customers(){

    return bankAccountService.listCustomer();}


    @GetMapping("/customer/{id}")
    public CustomerDto getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {

    return bankAccountService.getCustomer(customerId) ; }


    @PostMapping ("/customers")
public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){

return bankAccountService.saveCustomer(customerDto);}


    @PutMapping("/customers/{id}")
    public CustomerDto updarteCustomer(@PathVariable Long id,@RequestBody CustomerDto customerDto){

       customerDto.setId(id);
       return bankAccountService.updateCustomer(customerDto);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }

}
