package com.hope.xueling.common.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

/**
 * 大模型单次对话服务
 * @author 谢光益
 * @since 2026/1/26
 */
@AiService(wiringMode = EXPLICIT,chatModel = "DeepseekChatModel")//chatModel 对应的是 Spring Bean 容器中的 bean name，默认就是模型类名首字母小写的形式。
public interface SmartTranslation {

    /**
     * 翻译单词
     * @param message 单词
     * @return 翻译结果
     */
    @SystemMessage("/prompt/translateWord.txt")
    String translateWord(String message);
}
