package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JsonController {

    @GetMapping("/json")
    public Map<String, Object> returnJsonData() {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("name", "손국균");
        responseData.put("age", 25);
        return responseData;
    }
}
