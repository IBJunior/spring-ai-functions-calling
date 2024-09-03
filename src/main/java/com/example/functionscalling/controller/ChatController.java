package com.example.functionscalling.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @GetMapping("/simple")
    public String chat(@RequestParam String question) {
        return this.chatClient
                .prompt()
                .user(question)
                .options(OpenAiChatOptions.builder()
                        .withModel("ft:gpt-4o-mini-2024-07-18:personal:fly-intelligent:A1AK83lh")
                        .build())
                .call()
                .content();
    }

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
}
