package com.qtcoding.apiclientsdk.model.req;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xulongfei
 * @Description
 * @date 2023/10/15
 */
@Data
@Builder
public class AliUser implements Serializable {


    /**
     * 问题
     */
    private String message;

    /**
     * 角色
     */
    private String role;

    private String prompt;

    private Integer maxMessages;

}
