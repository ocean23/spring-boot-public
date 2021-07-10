package com.fujfu.template.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.fujfu.template.web.controller.mo.ConsumerMO;
import com.fujfu.template.web.controller.mo.ResponseMO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * @author Beldon
 */
@RequestMapping("/api/consumer")
@RestController
@Slf4j
public class ConsumerController {

    @GetMapping
    public ResponseMO<List<ConsumerMO>> allConsumer(){
        List<ConsumerMO> allConsumer = getAllConsumer();
        return ResponseMO.successWithData(allConsumer);
    }


    private List<ConsumerMO> getAllConsumer(){
        try (
                InputStream is = Files.newInputStream(Paths.get("./data.json"))
        ){
            final String data = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            return JSONArray.parseArray(data, ConsumerMO.class);
        } catch (IOException e) {
            log.error("parse data error", e);
            return Collections.emptyList();
        }
    }
}
