package com.UserService.User.Forms;
import lombok.Getter;
import org.apache.kafka.common.protocol.types.Field;

@Getter
public class VerifyForm {
    private String code;
    private String email;
    private String userName;
    private String reason;
    private String passWord;
}
