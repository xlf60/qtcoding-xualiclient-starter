package com.qtcoding.apiclientsdk.client;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.qtcoding.apiclientsdk.model.req.AliUser;
import com.qtcoding.apiclientsdk.model.resp.ResultResp;

/**
 * @author xulongfei
 * @Description 调用阿里云大模型接口的客户端
 * @date 2023/10/15
 */
public class XuAliClient {

    private String api_key;
    public XuAliClient(String api_key) {
        this.api_key = api_key;
    }
    public ResultResp callWithMessage(AliUser aliUser)
            throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(10);
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
//        {
//            "status_code": 200,
//                "request_id": "9da1ba31-b22a-9540-be18-793672d1ac8f",
//                "code": "",
//                "message": "",
//                "output": {
//            "text": null,
//                    "finish_reason": null,
//                    "choices": [
//            {
//                "finish_reason": "stop",
//                    "message": {
//                "role": "assistant",
//                        "content": "做西红柿鸡蛋的步骤如下：\n\n材料：\n- 鸡蛋 3 个\n- 西红柿 2 个\n-葱 适量\n- 蒜 适量\n- 盐 适量\n- 生抽 适量\n- 糖 适量\n- 胡椒粉 适量\n- 水淀粉 适量\n\n步骤：\n1. 西红柿去皮，切块；鸡蛋打散，加入适量盐和胡椒粉调味；\n2. 锅中加入适量油，倒入鸡蛋液，炒散；\n3. 加入葱蒜末，翻炒均匀；\n4. 加入西红柿块，翻炒至软烂；\n5. 加入适量生抽和糖，翻炒均匀；\n6. 最后加入适量水淀粉，翻炒均匀即可。"
//            }
//            }
//        ]
//        },
//            "usage": {
//            "input_tokens": 31,
//                    "output_tokens": 183
//        }
//        }
//        System.out.println(generationResult);
        return new ResultResp(generationResult.getOutput().getChoices().get(0).getMessage(),
                generationResult.getUsage());
    }

}
