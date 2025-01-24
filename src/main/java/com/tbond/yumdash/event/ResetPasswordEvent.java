package com.tbond.yumdash.event;

import com.tbond.yumdash.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResetPasswordEvent extends ApplicationEvent {
    private User user;

    public ResetPasswordEvent(User user) {
        super(user);
        this.user = user;
    }
}
