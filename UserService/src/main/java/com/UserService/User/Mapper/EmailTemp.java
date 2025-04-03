package com.UserService.User.Mapper;
import java.util.HashMap;
import java.util.Map;
public class EmailTemp {
    private static final Map<String,String> Subjects = new HashMap<>();
    private static final Map<String,String> Body = new HashMap<>();
    static {
        Subjects.put("OTP","OTP Code!");
        Body.put("OTP", "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">{content}</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>");
    }
    public static String getSubject(String key) {
        return Subjects.get(key);
    }
    public static String getBody(String key) {
        return Body.get(key);
    }
}
