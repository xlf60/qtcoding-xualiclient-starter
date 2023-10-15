package com.qtcoding.apiclientsdk.model.resp;

import com.alibaba.dashscope.aigc.generation.GenerationUsage;
import com.alibaba.dashscope.common.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResp {

   private Message message;
    private  GenerationUsage usage;

}
