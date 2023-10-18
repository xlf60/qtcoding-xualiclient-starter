package com.qtcoding.apiclientsdk.client;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.*;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import com.qtcoding.apiclientsdk.model.req.AliUser;
import com.qtcoding.apiclientsdk.model.resp.ResultResp;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Author xlf
 * Description 调用阿里云大模型接口的客户端
 * date 2023/10/15
 */
public class XuAliClient {
    private String api_key;

    public XuAliClient(String api_key) {
        this.api_key = api_key;
    }

    /**
     * 单轮对话
     */
    public ResultResp singleWithMessage(AliUser aliUser)
            throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        if (aliUser.getMaxMessages() == null) {
            aliUser.setMaxMessages(10);
        }
        MessageManager msgManager = new MessageManager(aliUser.getMaxMessages());
        Message systemMsg =
                Message.builder().role(Role.SYSTEM.getValue()).content(aliUser.getPrompt()).build();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(aliUser.getMessage()).build();
        msgManager.add(systemMsg);
        msgManager.add(userMsg);
        QwenParam param =
                QwenParam.builder().model(Generation.Models.QWEN_TURBO).messages(msgManager.get())
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .topP(0.8)
                        .enableSearch(true)
                        .apiKey(api_key)
                        .build();
        GenerationResult generationResult = gen.call(param);
        return new ResultResp(generationResult.getOutput().getChoices().get(0).getMessage(),
                generationResult.getUsage());
    }

    /**
     *  根据上下文联系对话
     */
    public List<History> moreWithMessage(List<History> histories, String msg)
            throws Exception {
        Generation gen = new Generation();
        QwenParam param = QwenParam.builder().model(Generation.Models.QWEN_TURBO)
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .prompt(msg)
                .history(histories)
                .topP(0.8).apiKey(api_key).build();
        GenerationResult generationResult = gen.call(param);
        String result = String.valueOf(generationResult.getOutput().getChoices().get(0).getMessage());
        histories.add(History.builder()
                .user(result)
                .bot(msg)
                .build());
        return histories;
    }
}
