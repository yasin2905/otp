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
public class WallixAuthResponseDto implements  Serializable {

    boolean success;
    WallixAuthResponseUserInfoDto user_info;


}