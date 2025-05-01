package online_market.user_app.controller;

import lombok.RequiredArgsConstructor;
import online_market.user_app.client.dadata.DadataRestClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class PromptAddresController {

    private final DadataRestClient dadataRestClient;

    @ResponseBody
    @GetMapping("/suggest")
    public List<String> suggest(@RequestParam String query) {
        return dadataRestClient.getListOfAddresses(query);
    }
}