package com.fujfu.template.web.controller.mo;


import com.fujfu.template.constant.ResponseConstants;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseMO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 成功 为0   失败 为 1
     */
    private int code = ResponseConstants.RESPONSE_CODE_SUCCESS;
    /**
     * 在code等于1(错误)时假如需要返回错误码的话可以用这个属性
     */
    private String errorCode;
    private String msg;
    private T data;
    private String debugInfo;

    public ResponseMO() {
    }

    public static <T> ResponseMO<T> response(int code, String msg) {
        ResponseMO<T> responseMO = new ResponseMO<>();
        responseMO.setCode(code);
        responseMO.setMsg(msg);
        return responseMO;
    }

    public static <T> ResponseMO<T> response(int code, String msg, T data, String debugInfo, String errorCode) {
        ResponseMO<T> responseMO = new ResponseMO<>();
        responseMO.setCode(code);
        responseMO.setMsg(msg);
        responseMO.setData(data);
        responseMO.setDebugInfo(debugInfo);
        responseMO.setErrorCode(errorCode);
        return responseMO;
    }

    public static <T> ResponseMO<T> success() {
        return response(ResponseConstants.RESPONSE_CODE_SUCCESS, null, null, null, null);
    }

    public static <T> ResponseMO<T> successWithMessage(String msg) {
        return response(ResponseConstants.RESPONSE_CODE_SUCCESS, msg, null, null, null);
    }

    public static <T> ResponseMO<T> successWithData(T data) {
        return response(ResponseConstants.RESPONSE_CODE_SUCCESS, null, data, null, null);
    }

    public static <T> ResponseMO<T> errorWithErrorCode(String errorCode, String msg) {
        return response(ResponseConstants.RESPONSE_CODE_FAILURE, msg, null, null, errorCode);
    }

    public static <T> ResponseMO<T> errorWithMessage(String msg) {
        return response(ResponseConstants.RESPONSE_CODE_FAILURE, msg, null, null, null);
    }

    public static <T> ResponseMO<T> errorWithDebugInfo(String msg, String debugInfo) {
        return response(ResponseConstants.RESPONSE_CODE_FAILURE, msg, null, debugInfo, null);
    }

    public boolean checkFailure() {
        boolean result = false;
        if (this.code == ResponseConstants.RESPONSE_CODE_FAILURE) {
            result = true;
        }
        return result;
    }
}
