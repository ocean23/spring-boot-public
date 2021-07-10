package com.fujfu.template.constant;

/**
 * Created by ocean on 06/19/2019
 */

public interface ResponseConstants {

    /**
     * 返回的data key
     */
    String RESPONSE_DATA_KEY = "data";

    // 返回码
    int RESPONSE_CODE_SUCCESS = 0;
    int RESPONSE_CODE_FAILURE = 1;
    int RESPONSE_CODE_RESOURCE_NOT_EXIST = 2;
    int RESPONSE_CODE_ANONYMOUS = 10;
    int RESPONSE_CODE_ACCESS_DENIED = 20;
    int RESPONSE_CODE_LOGIN_OFF = 100;

    String HEADER_USER_ID_KEY = "userId";
}
