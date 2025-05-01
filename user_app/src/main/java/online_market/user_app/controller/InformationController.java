package online_market.user_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/information-about-OMI")
public class InformationController {

    @GetMapping
    public String informationAboutOMI() {
        return "info/information-about-OMI";
    }
}
