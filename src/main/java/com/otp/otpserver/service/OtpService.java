package com.otp.otpserver.service;

import com.otp.otpserver.model.ConfirmRequestDto;
import com.otp.otpserver.model.ConfirmResponseDto;
import com.otp.otpserver.model.OtpRequestDto;
import com.otp.otpserver.model.OtpResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Value("${otp.expireTimeInSeconds}")
    private int expireTime;

    @Cacheable("otpCache")
    public String generateOtp(String userId, String domain) {
        String key = userId.concat("@").concat(domain).concat(LocalDateTime.now().toString());
        String otp = generateRandomNumber(key, 9999999, 1111111);
        String expireIn = LocalDateTime.now().minusSeconds(expireTime).toString();
        return otp.concat(";").concat(expireIn);

    }

    @CacheEvict(value = "otpCache", key = "#key")
    public void invalidateCache(String key) {
        // TODO
    }


    public String generateRandomNumber(String string, int maxRange, int minRange) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
            byte[] hash = md.digest(bytes);
            long hashValue = ByteBuffer.wrap(hash).getLong();
            Random random = new Random(hashValue);
            return String.valueOf(random.nextInt(maxRange - minRange + 1) + minRange);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }


}