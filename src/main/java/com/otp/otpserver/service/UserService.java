package com.otp.otpserver.service;

import com.otp.otpserver.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Value("${otp.expireTimeInSeconds}")
    private int expireTime;

    private final OtpService otpService;
    private final Map<String, String> authenticationList;
    private final Map<String, RegisterRequestDto> registeredUsers;

    public UserService(OtpService otpService, @Qualifier("AuthenticationList") Map<String, String> authenticationList,@Qualifier("RegisteredList") Map<String, RegisterRequestDto> registeredUsers) {
        this.otpService = otpService;
        this.authenticationList = authenticationList;
        this.registeredUsers = registeredUsers;
    }

    public OtpResponseDto generateOtp(OtpRequestDto requestDto) {

        String otp = otpService.generateOtp(requestDto.getUserId(), requestDto.getDomain());
        return OtpResponseDto.builder()
                .withDomain(requestDto.getDomain())
                .withUserId(requestDto.getUserId())
                .withExpireIn(expireTime)
                .withOtp(otp.split(";")[0])
//                .withGeneratedDateTime(LocalDateTime.now())
                .withExpiredDateTime(otp.split(";")[1])
                .newInstance();

    }
    public RegisterResponseDto registerUser(RegisterRequestDto requestDto) {

        RegisterRequestDto registerRequestDto = registeredUsers.get(requestDto.getUserName());
        if (registerRequestDto==null){
            registeredUsers.put(requestDto.getUserName(),requestDto);
            return RegisterResponseDto.builder()
                    .withUserId(requestDto.getUserName())
                    .withRegistered(true)
                    .newInstance();
        }else{
            return RegisterResponseDto.builder()
                    .withUserId(requestDto.getUserName())
                    .withRegistered(false)
                    .newInstance();
        }



    }


    public AuthResponseDto authenticationRequestGrant(AuthRequestDto requestDto) {

        RegisterRequestDto registerRequestDto = registeredUsers.get(requestDto.getUserId());
        if (registerRequestDto!=null){
            authenticationList.put(requestDto.getUserId().concat("@").concat(requestDto.getDomain()), "0");

            return AuthResponseDto.builder()
                    .withDomain(requestDto.getDomain())
                    .withUserId(requestDto.getUserId())
                    .withHasAuthenticationRequest(true)
                    .withIsRegistered(true)
                    .newInstance();
        }else{
            return AuthResponseDto.builder()
                    .withDomain(requestDto.getDomain())
                    .withUserId(requestDto.getUserId())
                    .withHasAuthenticationRequest(false)
                    .withIsRegistered(false)
                    .newInstance();
        }

    }
    public AuthResponseDto authenticationFetchRequest(AuthRequestDto requestDto) {

        String authFlag = authenticationList.get(requestDto.getUserId().concat("@").concat(requestDto.getDomain()));
//        authenticationList.remove(requestDto.getUserId().concat("@").concat(requestDto.getDomain()));
        if (authFlag != null) {
            if (authFlag.equals("0")){
                return AuthResponseDto.builder()
                        .withDomain(requestDto.getDomain())
                        .withUserId(requestDto.getUserId())
                        .withHasAuthenticationRequest(true)
                        .newInstance();
            }else{
                return AuthResponseDto.builder()
                        .withDomain(requestDto.getDomain())
                        .withUserId(requestDto.getUserId())
                        .withHasAuthenticationRequest(false)
                        .newInstance();
            }
        } else {
            return AuthResponseDto.builder()
                    .withDomain(requestDto.getDomain())
                    .withUserId(requestDto.getUserId())
                    .withHasAuthenticationRequest(false)
                    .newInstance();
        }

    }

    public AuthResponseDto authRequestFetchGrant(AuthRequestDto requestDto) {

        String authFlag = authenticationList.get(requestDto.getUserId().concat("@").concat(requestDto.getDomain()));
        if (authFlag != null) {
            if (authFlag.equals("-1")){
                return AuthResponseDto.builder()
                        .withDomain(requestDto.getDomain())
                        .withUserId(requestDto.getUserId())
                        .withHasAuthenticationRequest(true)
                        .withAuthResult(authFlag.equals("1"))
                        .withRejected(true)
                        .newInstance();
            }else {

                return AuthResponseDto.builder()
                        .withDomain(requestDto.getDomain())
                        .withUserId(requestDto.getUserId())
                        .withHasAuthenticationRequest(true)
                        .withAuthResult(authFlag.equals("1"))
                        .withInProgress(false)
                        .newInstance();
            }
        } else {
            return AuthResponseDto.builder()
                    .withDomain(requestDto.getDomain())
                    .withUserId(requestDto.getUserId())
                    .withHasAuthenticationRequest(false)
//                    .withAuthResult(authFlag.equals("1"))
                    .newInstance();
        }

    }
    public AuthResponseDto authenticationGenerateGrant(AuthRequestDto requestDto) {

        authenticationList.put(requestDto.getUserId().concat("@").concat(requestDto.getDomain()), "1");

        return AuthResponseDto.builder()
                .withDomain(requestDto.getDomain())
                .withUserId(requestDto.getUserId())
                .withGranted(true)
                .newInstance();
    }

    public ConfirmResponseDto confirm(ConfirmRequestDto requestDto) {
        String otp = otpService.generateOtp(requestDto.getUserId(), requestDto.getDomain());
        boolean otpCheck = otp.split(";")[0].equals(requestDto.getOtp());
        return ConfirmResponseDto.builder()
                .withDomain(requestDto.getDomain())
                .withUserId(requestDto.getUserId())
                .withOtp(requestDto.getOtp())
                .withOtpCheck(otpCheck)
                .newInstance();
    }



    public AuthResponseDto rejectAccess(AuthRequestDto requestDto) {

        authenticationList.put(requestDto.getUserId().concat("@").concat(requestDto.getDomain()), "-1");

        return AuthResponseDto.builder()
                .withDomain(requestDto.getDomain())
                .withUserId(requestDto.getUserId())
                .withGranted(false)
                .newInstance();
    }


    public WallixAuthResponseDto wallixAuth(WallixAuthRequestDto requestDto) {
        AuthRequestDto authRequestDto=AuthRequestDto.builder()
                .withUserId(requestDto.getUsername())
                .withDomain("wallix")
                .newInstance();
        authenticationRequestGrant(authRequestDto);

        WallixAuthResponseUserInfoDto wallixAuthResponseUserInfoDto=WallixAuthResponseUserInfoDto
                .builder()
                .withUid(requestDto.getUsername().concat("@wallix.com"))
                .withCn(requestDto.getUsername())
                .withMail(requestDto.getUsername().concat("@wallix.com"))
                .withGroups(new String[]{"test","develop"})
                .newInstance();

            int countOfTry=0;



            while (countOfTry++ <10){
                AuthResponseDto authResponseDto = authRequestFetchGrant(authRequestDto);
                if (authResponseDto.isInProgress() &&
                        authResponseDto.isAuthResult() &&
                        authResponseDto.isHasAuthenticationRequest()
                ){
                    return WallixAuthResponseDto.builder()
                            .withSuccess(true)
                            .withUser_info(wallixAuthResponseUserInfoDto)
                            .newInstance();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            return WallixAuthResponseDto.builder()
                    .withSuccess(false)
                    .withUser_info(wallixAuthResponseUserInfoDto)
                    .newInstance();



    }
}