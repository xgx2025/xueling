package com.hope.xueling.common.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 大语言模型配置类
 * @author 谢光湘
 * @since 2026/1/26
 */
@Configuration
public class LLMConfig {
    /**
     * 豆包模型的相关配置
     */
    @Value("${llm.doubao.api-key}")
    private String doubaoApiKey;
    @Value("${llm.doubao.model}")
    private String doubaoModel;
    @Value("${llm.doubao.url}")
    private String doubaoUrl;
    /**
     * DeepSeek模型的相关配置
     */
    @Value("${llm.deepseek.api-key}")
    private String deepSeekApiKey;
    @Value("${llm.deepseek.url}")
    private String deepSeekUrl;
    @Value("${llm.deepseek.model}")
    private String deepSeekModel;


    /**
     * 配置豆包聊天模型
     * @return ChatModel
     */
    @Bean("doubaoChatModel")
    public ChatModel doubaoChatModel() {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .apiKey(doubaoApiKey)
                .baseUrl(doubaoUrl)
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(doubaoModel)
                        .build())
                .build();
    }


    /**
     * 配置DeepSeek聊天模型
     * @return ChatModel
     */
    @Bean("deepseekChatModel")
    public ChatModel deepseekChatModel() {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .apiKey(deepSeekApiKey)
                .baseUrl(deepSeekUrl)
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(deepSeekModel)
                        .build())
                .build();
    }
}
