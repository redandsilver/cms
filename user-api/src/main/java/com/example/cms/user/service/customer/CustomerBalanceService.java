package com.example.cms.user.service.customer;

import com.example.cms.user.domain.ChangeBalanceForm;
import com.example.cms.user.domain.model.CustomerBalanceHistory;
import com.example.cms.user.domain.repository.CustomerBalanceHistoryRepository;
import com.example.cms.user.domain.repository.CustomerRepository;
import com.example.cms.user.exception.CustomException;
import com.example.cms.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerBalanceService {
    private final CustomerBalanceHistoryRepository customerBalanceHistoryRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerBalanceHistory changeBalance(Long customerId, ChangeBalanceForm form)
    throws CustomException{
        CustomerBalanceHistory customerBalanceHistory =
                customerBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(customerId)
                        .orElse(CustomerBalanceHistory.builder()
                                .changeMoney(0)
                                .currentMoney(0)
                                .customer(customerRepository.findById(customerId)
                                        .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER)))
                                .build());
        if(customerBalanceHistory.getCurrentMoney() + form.getMoney() < 0){
            throw new CustomException(ErrorCode.NOT_ENOUGH_BALANCE);
        }
        customerBalanceHistory = CustomerBalanceHistory.builder()
                .changeMoney(form.getMoney())
                .currentMoney(customerBalanceHistory.getCurrentMoney()+form.getMoney())
                .description(form.getMessage())
                .fromMessage(form.getFrom())
                .customer(customerBalanceHistory.getCustomer())
                .build();
        customerBalanceHistory.getCustomer().setBalance(customerBalanceHistory.getCurrentMoney());
        return customerBalanceHistoryRepository.save(customerBalanceHistory);

    }

}
