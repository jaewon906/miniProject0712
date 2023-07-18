package com.project0712.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTO {
    private Long id;
    private String userId;
    private String userEmail;
    private String verificationCode;
}
