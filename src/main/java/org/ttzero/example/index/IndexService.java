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

import org.springframework.stereotype.Service;

/**
 * @author guanquan.wang at 2020-06-02 14:34
 */
@Service
public class IndexService {
    public boolean sign(String account, String password) {
         return "admin".equalsIgnoreCase(account) && "admin".equals(password);
    }
}
