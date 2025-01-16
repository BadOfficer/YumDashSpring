package com.tbond.yumdash.event;

import com.tbond.yumdash.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class EmailVerificationEvent extends ApplicationEvent {
    private User user;

    private String applicationUrl;

    public EmailVerificationEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
