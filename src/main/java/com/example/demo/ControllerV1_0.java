package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiVersionedResource(version="1.0.x")
public class ControllerV1_0 {

    @RequestMapping("/version")
    public String getVersion() {
        return "1.0.0";
    }
}
