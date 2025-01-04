package com.tracker;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/self-appreciation")
public class SelfAppreciationController {

    @Autowired
    private SelfAppreciationService selfAppreciationService;

    // Display the self-appreciation page with the list of entries
    @GetMapping
    public String getSelfAppreciations(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            return "redirect:/login";
        }
        List<SelfAppreciation> appreciations = selfAppreciationService.getAllAppreciationsByUser(user.getUsername());
        model.addAttribute("appreciations", appreciations);
        return "self-appreciation"; // View template name
    }

    // Handle adding a new self-appreciation note
    @PostMapping
    public String addSelfAppreciation(@AuthenticationPrincipal User user, @RequestParam String appreciationText) {
        if (user == null) {
            return "redirect:/login";
        }
        selfAppreciationService.addAppreciation(user.getUsername(), appreciationText);
        return "redirect:/self-appreciation";
    }
}
