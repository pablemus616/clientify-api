package com.nihil.clientifyapi.ai;

import org.springframework.web.bind.annotation.RestController;

@RestController("ai")
public class AiController {
    private final AiService aiService;
    public AiController(AiService _aiService){
        this.aiService = _aiService;
    }
}
