package com.UserService.User.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
public class DigitCode {
    @Value("${spring.secretKey}")
    private String secret;
    @Value("${spring.expiryTime}")
    private int expiryTime;
    @Value("${spring.count}")
    private int count;
    private static final Logger log = LoggerFactory.getLogger(DigitCode.class);

    private long getTime() {
        return System.currentTimeMillis() / (expiryTime * 1000 * 60);
    }
    private String encode(String userName) {
        long time = getTime();
        long code = 0;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            String input = userName + time;
            byte[] hash = mac.doFinal(input.getBytes());

            for (int i = 0; i < count; i++) {
                code = (code * 10) + (hash[i] & 0xFF) % 10;
            }
        } catch (Exception e) {
            log.error("Error generating code: " + e.getMessage());
        }
        return String.format("%08d", code);
    }
    public boolean verify(String email, String code)throws Exception{
        if (encode(email).equals(code)){
            return true;
        }
        throw new Exception("");
    }
    public String getCode(String email)throws Exception {
        return encode(email);
    }
}
