package com.otp.otpserver.api;


import com.otp.otpserver.model.*;
import com.otp.otpserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(value = "/")
public class OtpController {


    private final UserService userService;

    public OtpController(UserService userService) {
        this.userService = userService;
    }


    @CrossOrigin(origins = "*")
    @PostMapping(path = "/generate-otp")
    public OtpResponseDto generate(
            HttpServletRequest httpServletRequest,
            @RequestBody OtpRequestDto request,
            @RequestHeader Map<String, String> headers
    ) {

        return userService.generateOtp(request);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/register")
    public RegisterResponseDto register(
            HttpServletRequest httpServletRequest,
            @RequestBody RegisterRequestDto request,
            @RequestHeader Map<String, String> headers
    ) {

        return userService.registerUser(request);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/confirm-otp")
    public ConfirmResponseDto confirm(
            HttpServletRequest httpServletRequest,
            @RequestBody ConfirmRequestDto request,

            @RequestHeader Map<String, String> headers
    ) {

        return userService.confirm(request);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/request-grant")
    public AuthResponseDto requestGrant(
            HttpServletRequest httpServletRequest,
            @RequestBody AuthRequestDto request,

            @RequestHeader Map<String, String> headers
    ) {

        return userService.authenticationRequestGrant(request);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/fetch-request")
    public AuthResponseDto fetchRequest(
            HttpServletRequest httpServletRequest,
            @RequestBody AuthRequestDto request,

            @RequestHeader Map<String, String> headers
    ) {

        return userService.authenticationFetchRequest(request);
    }


    @CrossOrigin(origins = "*")
    @PostMapping(path = "/generate-grant")
    public AuthResponseDto generateGrant(
            HttpServletRequest httpServletRequest,
            @RequestBody AuthRequestDto request,

            @RequestHeader Map<String, String> headers
    ) {

        return userService.authenticationGenerateGrant(request);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/reject-access")
    public AuthResponseDto rejectAccess(
            HttpServletRequest httpServletRequest,
            @RequestBody AuthRequestDto request,

            @RequestHeader Map<String, String> headers
    ) {

        return userService.rejectAccess(request);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/fetch-grant")
    public AuthResponseDto fetchGrant(
            HttpServletRequest httpServletRequest,
            @RequestBody AuthRequestDto request,

            @RequestHeader Map<String, String> headers
    ) {

        return userService.authRequestFetchStatus(request);
    }


}