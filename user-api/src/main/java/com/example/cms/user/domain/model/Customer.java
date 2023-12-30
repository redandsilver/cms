package com.example.cms.user.domain.model;

import com.example.cms.user.domain.SignUpForm;
import com.example.cms.user.type.UserStatus;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
public class Customer extends BaseEntity{
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String name;
    private String phone;
    private LocalDate birth;
    private String password;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;

    // db상에 0또는1이 아닌 문자열형태로 저장
    @Enumerated(EnumType.STRING)
    private UserStatus verify;

    public static Customer from(SignUpForm form){
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .phone(form.getPhone())
                .birth(form.getBirth())
                .verify(UserStatus.UN_VERIFIED)
                .build();
    }

    public Enum<UserStatus> isVerify() {
        return this.verify;
    }
}
