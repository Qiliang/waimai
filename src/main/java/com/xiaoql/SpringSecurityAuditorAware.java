package com.xiaoql;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware  implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return null;
    }
}