package com.cyecize.wakeonlan;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("isAnonymous()")
@RequiredArgsConstructor
public class SecurityController {

    @Value("${admin.username}")
    private final String username;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login.html").addObject("username", this.username);
    }
}
