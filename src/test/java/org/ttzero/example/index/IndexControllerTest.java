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


package org.ttzero.example.index;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author guanquan.wang at 2020-06-02 15:08
 */

@SpringBootTest
@AutoConfigureMockMvc
class IndexControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testNotSigned() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("signin"))
            .andExpect(content().string(containsString("SIGN IN")));
    }

    @Test
    void testSigned() throws Exception {
        this.mockMvc.perform(get("/").cookie(new Cookie("auth", "abc"))).andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(content().string(containsString("欢迎您：ADMIN")));
    }

    @Test
    void testSignIn() throws Exception {
        this.mockMvc.perform(post("/signin").param("account", "abc").param("password", "abc")).andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("帐号/密码不匹配")));
    }

    @Test
    void testSignIn1() throws Exception {
        this.mockMvc.perform(post("/signin").param("account", "admin").param("password", "admin")).andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(content().string(containsString("欢迎您：ADMIN")))
            .andExpect(cookie().exists("auth"));
    }

    @Test
    void testSignIn2() throws Exception {
        this.mockMvc.perform(post("/signin").param("account", "").param("password", "")).andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("signin"));
    }

    @Test
    void testSignOut() throws Exception {
        this.mockMvc.perform(post("/signout").cookie(new Cookie("auth", "abc"))).andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("signin"))
            .andExpect(cookie().value("auth", Matchers.nullValue()));
    }
}
