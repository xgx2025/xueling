package com.hope.xueling.common.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * LLM配置类
 * @author 谢光益
 * @since 2026/1/26
 */
@Configuration
public class LLMConfig {

    //大模型的API密钥
    @Value("${deepseek_key}")
    private String deepSeekApiKey;


    /**
     * 获取大模型-deepseek-chat
     * @return
     */
    public ChatLanguageModel DeepSeekModel() {
        return OpenAiChatModel.builder()
                .baseUrl("https://api.deepseek.com")
                .modelName("deepseek-chat")
                .apiKey(deepSeekApiKey)
                .logRequests(true)
                .logResponses(true)
                .maxTokens(1000)
                .build();
    }

}
