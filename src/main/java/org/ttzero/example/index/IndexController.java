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

import org.ttzero.example.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author guanquan.wang at 2020-06-02 13:30
 */
@RestController
public class IndexController {

    private final IndexService service;

    @Autowired
    public IndexController(IndexService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ModelAndView init(HttpServletRequest request, Map<String, Object> model) {
        if (isSigined(request)) {
            model.put("title", "abc");
            model.put("user", "ADMIN");
            return new ModelAndView("index", model);
        } else {
            return new ModelAndView("signin", model);
        }
    }

    @PostMapping("/signin")
    public ModelAndView signin(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String account = request.getParameter("account")
            , password = request.getParameter("password");
        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(password)) {
            return new ModelAndView("signin", model);
        }
        if (service.sign(account, password)) {
            model.put("title", "首页");
            model.put("user", "ADMIN");
            Cookie cookie = new Cookie("auth", StringUtil.getRandomString(11));
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new ModelAndView("index", model);
        } else {
            model.put("title", "错误!!");
            model.put("message", "帐号/密码不匹配");
            model.put("account", request.getParameter("account"));
            return new ModelAndView("signin", model);
        }
    }

    @PostMapping("/signout")
    public ModelAndView signout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        if (isSigined(request)) {
            Cookie cookie = new Cookie("auth", null);
            cookie.setMaxAge(0); // Deleted
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            model.put("message", "成功退出");
        }
        return new ModelAndView("signin", model);
    }

    private boolean isSigined(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth".equals(cookie.getName()) && !cookie.getValue().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

}
