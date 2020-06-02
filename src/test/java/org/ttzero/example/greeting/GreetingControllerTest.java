/*
 * Copyright (c) 2017-2020, guanquan.wang@yandex.com All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.ttzero.example.greeting;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ttzero.example.index.Auth;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

/**
 * @author guanquan.wang at 2020-06-02 11:59
 */
@WebMvcTest(GreetingController.class)
class GreetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService service;

    @Test
    void testGreeting() throws Exception {
        when(service.greet()).thenReturn("Hello, Mock");
        this.mockMvc.perform(get("/greeting")).andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello, Mock")));
    }

    @Rule
    public Timeout timeout = new Timeout(100, TimeUnit.MILLISECONDS);

    @Test
    void testPutting() throws Exception {
        Auth auth = new Auth();
        auth.setAccount("a");
        auth.setPassword("b");
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(post("/putting").content(mapper.writeValueAsBytes(auth))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(mapper.writeValueAsString(auth))));
    }


}
