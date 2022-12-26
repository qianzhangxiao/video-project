package com.qzx.user.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.qzx.user.entity.VoUserEntity;
import com.qzx.user.utils.ResponseResult;

/**
 * BlockException处理流控、降级等异常
 * */
public class SentinelBlockExceptionHandler {
    // 服务流量控制处理，注意细节，一定要跟原函数的返回值和形参一致，并且形参最后要加个BlockException参数，否则会报错，FlowException: null
    public static ResponseResult<?> getCaptchaBlockHandler(BlockException ex) {
        System.out.println("selectUserByNameBlockHandler异常信息：" + ex.getMessage());
        return handleException(ex);
    }

    public static ResponseResult<?> registerBlockHandler(VoUserEntity user,BlockException ex){
        System.out.println("selectUserByNameBlockHandler异常信息：" + ex.getMessage());
        return handleException(ex);
    }


    private static ResponseResult<?> handleException(BlockException ex){
        if (ex instanceof FlowException){
            return ResponseResult.error(500,"服务器过载，请稍后重试!");
        }
        if (ex instanceof DegradeException){
            return ResponseResult.error(500,"响应较慢，请稍后重试!");
        }
        if(ex instanceof ParamFlowException){
            return ResponseResult.error(500,"热点参数限流!");
        }
        if(ex instanceof SystemBlockException){
            return ResponseResult.error(500,"触发系统保护规则!");
        }
        if(ex instanceof AuthorityException){
            return ResponseResult.error(500,"授权规则不通过!");
        }
        return ResponseResult.error(500,"当前服务访问流量过大!");
    }

}
