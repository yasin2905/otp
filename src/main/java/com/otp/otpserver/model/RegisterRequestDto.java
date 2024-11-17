package com.otp.otpserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(buildMethodName = "newInstance", setterPrefix = "with")
public class RegisterRequestDto implements  Serializable {

    String firstName;
    String lastName;
    String username;
    String cellphoneNumber;
    String address;


}
