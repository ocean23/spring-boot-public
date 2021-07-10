package com.fujfu.template.web.controller;

import com.fujfu.template.web.controller.mo.DemoMO;
import com.fujfu.template.web.controller.mo.ResponseMO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ocean on 09/23/2020
 */
@RequestMapping("/demo")
@RestController
public class DemoController {

    @GetMapping("/hello")
    public ResponseMO<DemoMO> hello() {
        DemoMO demo = new DemoMO();
        demo.setName("hello world");
        return ResponseMO.successWithData(demo);
    }
}
