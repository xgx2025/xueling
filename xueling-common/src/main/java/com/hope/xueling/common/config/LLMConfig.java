package com.hope.xueling.common.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
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
     * DeepSeekChat模型的相关配置
     */
    @Value("${llm.deepseek.api-key}")
    private String deepSeekApiKey;
    @Value("${llm.deepseek.url}")
    private String deepSeekUrl;
    @Value("${llm.deepseek.chat-model}")
    private String deepSeekChatModel;
    @Value("${llm.deepseek.reasoner-model}")
    private String deepSeekReasonerModel;



    /**
     * 配置豆包聊天模型
     * @return ChatModel
     */
    @Bean("springDoubaoChatModel")
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
    @Bean("springDeepseekChatModel")
    public ChatModel deepseekChatModel() {
        OpenAiApi openAiApi = OpenAiApi.builder()
                .apiKey(deepSeekApiKey)
                .baseUrl(deepSeekUrl)
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model(deepSeekChatModel)
                        .build())
                .build();
    }


    //langchain4j

    /**
     * 配置deepseek-chat模型
     * @return ChatLanguageModel
     */
    @Bean("DeepseekChatModel")
    public ChatLanguageModel DeepSeekChatModel() {
        return dev.langchain4j.model.openai.OpenAiChatModel.builder()
                .baseUrl(deepSeekUrl)
                .modelName(deepSeekChatModel)
                .apiKey(deepSeekApiKey)
                .maxTokens(1024)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    /**
     * 配置deepseek-chat流式输出模型
     * @return ChatLanguageModel
     */
    @Bean("DeepseekChatStreamingModel")
    public StreamingChatLanguageModel DeepSeekChatStreamingModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl(deepSeekUrl)
                .modelName(deepSeekChatModel)
                .apiKey(deepSeekApiKey)
                .maxTokens(1024)
                .logRequests(true)
                .logResponses(true)
                .build();
    }
    /**
     * 配置deepseek-reasoner模型
     * @return ChatLanguageModel
     */
    @Bean("DeepseekReasonerModel")
    public ChatLanguageModel DeepSeekReasonerModel() {
        return dev.langchain4j.model.openai.OpenAiChatModel.builder()
                .baseUrl(deepSeekUrl)
                .modelName(deepSeekReasonerModel)
                .apiKey(deepSeekApiKey)
                .maxTokens(1024)
                .logRequests(true)
                .logResponses(true)
                .build();
    }
    /**
     * 配置deepseek-reasoner流式输出模型
     * @return ChatLanguageModel
     */
     @Bean("DeepseekReasonerStreamingModel")
    public StreamingChatLanguageModel DeepSeekReasonerStreamingModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl(deepSeekUrl)
                .modelName(deepSeekReasonerModel)
                .apiKey(deepSeekApiKey)
                .maxTokens(1024)
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
