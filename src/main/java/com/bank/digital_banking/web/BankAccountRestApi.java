package com.bank.digital_banking.web;

import com.bank.digital_banking.Exceptions.BankAccountNotFoundException;
import com.bank.digital_banking.dtos.AccountHistoryDTO;
import com.bank.digital_banking.dtos.AccountOperationDTO;
import com.bank.digital_banking.dtos.BankAccountDTO;
import com.bank.digital_banking.services.BankAccountService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional
public class BankAccountRestApi {

private BankAccountService bankAccountService;

public BankAccountRestApi(BankAccountService bankAccountService){
    this.bankAccountService=bankAccountService;
}



@GetMapping("/accounts/{accountId}")
public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {

return bankAccountService.getBankAccount(accountId);}


    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){

    return bankAccountService.bankAccountList();}


    @GetMapping("/accounts/{AccountId}/operations")
public List<AccountOperationDTO>  getHistory(@PathVariable String AccountId){

return bankAccountService.accountHistory(AccountId);}


    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO  getAccountHistory(@PathVariable String accountId,
                                                      @RequestParam(name="page",defaultValue = "0") int page,
                                                      @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {


        return bankAccountService.getAccountHistory(accountId, page, size);

    }

}













