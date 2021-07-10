package com.fujfu.template.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fujfu.template.util.JsonObjectUtil;
import com.fujfu.template.web.controller.mo.ResponseMO;
import com.fujfu.template.web.util.MockUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

/**
 * @author ocean
 * @date 2021/7/10 11:32
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseControllerTest {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    private final static String UTF_8_CODE = "utf-8";

    public <T> ResponseMO<T> performGetSingle(String urlPath, Class<T> dataMO) {
        MvcResult mvcResult = null;
        ResponseMO<T> responseMO = null;
        try {
            mvcResult = mockMvc.perform(MockUtility.populateGetBuilder(urlPath)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseMO = JsonObjectUtil.parseSingle(resultStr, dataMO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseMO;
    }

    public <T> ResponseMO<List<T>> performGetList(String urlPath, Class<T> dataMO) {
        MvcResult mvcResult = null;
        ResponseMO<List<T>> responseList = null;
        try {
            mvcResult = mockMvc.perform(MockUtility.populateGetBuilder(urlPath)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseList = JsonObjectUtil.parseList(resultStr, dataMO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseList;
    }

    public ResponseMO performBodyPutSingle(String urlPath) {
        MvcResult mvcResult = null;
        ResponseMO responseMO = null;
        try {
            mvcResult = mockMvc.perform(MockUtility.populatePutBuilder(urlPath)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseMO = JSONObject.parseObject(resultStr, ResponseMO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseMO;
    }

    public ResponseMO performBodyPutSingle(String urlPath, Object query) {
        MvcResult mvcResult = null;
        ResponseMO responseMO = null;
        try {
            mvcResult = mockMvc.perform(MockUtility.populateBodyPutBuilder(urlPath,query)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseMO = JSONObject.parseObject(resultStr, ResponseMO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseMO;
    }

    public ResponseMO performPostSingle(String urlPath) {
        MvcResult mvcResult;
        ResponseMO responseMO;
        try {
            mvcResult = mockMvc.perform(MockUtility.populatePostBuilder(urlPath)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseMO = JSONObject.parseObject(resultStr, ResponseMO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseMO;
    }

    public <T> ResponseMO<T> performPostSingle(String urlPath, Class<T> dataMO) {
        MvcResult mvcResult = null;
        ResponseMO<T> responseMO = null;
        try {
            mvcResult = mockMvc.perform(MockUtility.populatePostBuilder(urlPath)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseMO = JsonObjectUtil.parseSingle(resultStr, dataMO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseMO;
    }

    public ResponseMO performBodyPostSingle(String urlPath, Object query) {
        MvcResult mvcResult;
        ResponseMO responseMO;
        try {
            mvcResult = mockMvc.perform(MockUtility.populateBodyPostBuilder(urlPath,query)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseMO = JSONObject.parseObject(resultStr, ResponseMO.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseMO;
    }

    public <T> ResponseMO<T> performBodyPostSingle(String urlPath, Object query ,Class<T> dataMO) {
        MvcResult mvcResult = null;
        ResponseMO<T> responseMO = null;
        try {
            mvcResult = mockMvc.perform(MockUtility.populateBodyPostBuilder(urlPath,query)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseMO = JsonObjectUtil.parseSingle(resultStr, dataMO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseMO;
    }

    public <T> ResponseMO<List<T>> performBodyPostList(String urlPath, Object query, Class<T> dataMO) {
        MvcResult mvcResult = null;
        ResponseMO<List<T>> responseList = null;
        try {
            mvcResult = mockMvc.perform(MockUtility.populateBodyPostBuilder(urlPath,query)).andReturn();
            mvcResult.getResponse().setCharacterEncoding(UTF_8_CODE);
            String resultStr = mvcResult.getResponse().getContentAsString();
            responseList = JsonObjectUtil.parseList(resultStr, dataMO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return responseList;
    }
}
