package com.meiwenhui.springbootjenkinsdocker;

import com.meiwenhui.springbootjenkinsdocker.controller.WelcomeController;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class AppTests {

    //测试
    @Test
    void index() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new WelcomeController()).build();
        String result = mockMvc.perform(get("/")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        JSONObject json = new JSONObject(result);
        Long timestamp = json.getLong("timestamp");
        Assert.isTrue(timestamp % 2 == 0, "当前【" + timestamp + "】不是偶数");
    }

}
