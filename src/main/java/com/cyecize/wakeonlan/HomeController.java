package com.cyecize.wakeonlan;

import com.cyecize.wakeonlan.api.ping.PingService;
import com.cyecize.wakeonlan.api.wakeup.WakeUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isFullyAuthenticated()")
public class HomeController {

    @Value("${target.address.ip}")
    private final String targetIp;

    @Value("${target.address.mac}")
    private final String targetMac;

    @Value("${wakeup.wait}")
    private final int wait;

    private final PingService pingService;

    private final WakeUpService wakeUpService;


    @GetMapping("/")
    public ModelAndView home(Model model) {
        final int wait = Objects.requireNonNullElse((Integer) model.getAttribute("wait"), 2000);

        return new ModelAndView("home.html")
                .addObject("awake", this.pingService.ping(this.targetIp, wait));
    }

    @PostMapping("/")
    public ModelAndView wakeUpPc(RedirectAttributes redirectAttributes) {
        this.wakeUpService.sendMagicPacket(this.targetIp, this.targetMac);
        redirectAttributes.addFlashAttribute("wait", this.wait);

        return new ModelAndView("redirect:/");
    }
}
