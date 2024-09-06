package com.example.functionscalling.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/tools")
public class FunctionController {
    @Value("classpath:prompts/best-destination-prompt.st")
    private Resource bestDestinationPrompt;
    @Value("classpath:prompts/get-flights-prompt.st")
    private Resource getFlightsPrompt;
    @Value("classpath:prompts/default-system-message.st")
    private Resource defaultSystemMessage;

    private final ChatClient chatClient;

    public FunctionController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    // Using multiple functions
    @GetMapping("/destinations")
    public String getBestDestinations(@RequestParam String season, @RequestParam String days) {
        // Create User Message
        PromptTemplate promptTemplate = new PromptTemplate(bestDestinationPrompt);
        Message message = promptTemplate.createMessage(Map.of("season", season, "days", days));

        // Create System Message
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(defaultSystemMessage);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("userId", ""));

        // Create the Prompt
        Prompt prompt = new Prompt(List.of(message, systemMessage), OpenAiChatOptions.builder()
                .withFunctions(Set.of("getDestinationBySeasons",
                        "getBudgetByDestinationAndNumberOfDays"))
                .build());

        return this.chatClient
                .prompt(prompt)
                .call()
                .content();
    }

    @GetMapping("/flights")
    public String getFlights(@RequestParam Long clientId) {

        // Create User Message
        UserMessage userMessage = new UserMessage(getFlightsPrompt);

        // Create System Message
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(defaultSystemMessage);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("userId", clientId));

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage), OpenAiChatOptions.builder()
                .withFunction("getFlightsByUser")
                .build());

        return this.chatClient
                .prompt(prompt)
                .call()
                .content();

    }
}
