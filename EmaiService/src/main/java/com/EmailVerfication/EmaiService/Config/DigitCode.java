package com.EmailVerfication.EmaiService.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class DigitCode {
    @Value("${secretKey}")
    private String secret;
    @Value("${expiryTime}")
    private int expiryTime;
    @Value("${count}")
    private int count;
    private static final Logger log = LoggerFactory.getLogger(DigitCode.class);

    private long getTime() {
        return System.currentTimeMillis() / (expiryTime * 1000 * 60);
    }
    private String encode(String email) {
        long time = getTime();
        long code = 0;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            String input = email + time;
            byte[] hash = mac.doFinal(input.getBytes());

            for (int i = 0; i < count; i++) {
                code = (code * 10) + (hash[i] & 0xFF) % 10;
            }
        } catch (Exception e) {
            log.error("Error generating code: " + e.getMessage());
        }
        return String.format("%08d", code);
    }
    public boolean verify(String email, String code) {
        if (encode(email).equals(code)){
            return true;
        }
        return false;
    }
    public String getCode(String email) {
        return encode(email);
    }
}
