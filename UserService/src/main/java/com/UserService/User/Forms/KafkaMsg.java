package com.UserService.User.Forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaMsg {
    private String subject;
    private String body;

    @Override
    public String toString() {
        return subject + "," + body;
    }
}

