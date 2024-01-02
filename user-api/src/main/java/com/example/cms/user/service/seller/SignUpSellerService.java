package com.example.cms.user.service.seller;
import com.example.cms.user.domain.SignUpForm;
import com.example.cms.user.domain.model.Customer;
import com.example.cms.user.domain.model.Seller;
import com.example.cms.user.domain.repository.CustomerRepository;
import com.example.cms.user.domain.repository.SellerRepository;
import com.example.cms.user.exception.CustomException;
import com.example.cms.user.exception.ErrorCode;
import com.example.cms.user.type.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignUpSellerService {
    private final SellerRepository sellerRepository;
    public Seller signUp(SignUpForm form){
        return sellerRepository.save(Seller.from(form));
    }

    public boolean isEmailExist(String email){
        return sellerRepository.findByEmail(
                email.toLowerCase(Locale.ROOT)).isPresent();
    }
    @Transactional
    public void verifyEmail(String email, String code){
        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_EXIST_USER));

        if(seller.isVerify()){
            throw new CustomException(ErrorCode.ALREADY_VERIFIED);
        }
        else if(!seller.getVerificationCode().equals(code)){
            throw new CustomException(ErrorCode.WRONG_VERIFICATION);
        }
        else if(seller.getVerifyExpiredAt().isBefore(LocalDateTime.now())){
            throw new CustomException(ErrorCode.EXPIRE_CODE);
        }
        seller.setVerify(true);
    }
    @Transactional
    public LocalDateTime changeSellerValidateEmail(Long sellerId, String verificationCode){
        Optional<Seller> sellerOptional= sellerRepository.findById(sellerId);

        if(sellerOptional.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }else{
            Seller seller =  sellerOptional.get();
            seller.setVerificationCode(verificationCode);
            seller.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return seller.getVerifyExpiredAt();
        }
    }
}
