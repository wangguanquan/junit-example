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


package org.ttzero.example.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author guanquan.wang at 2020-06-02 14:36
 */
public class StringUtil {
    public static boolean isEmpty(String s) {
        return s == null
            || s.isEmpty();
    }

    private static char[] charArray = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private static ThreadLocal<char[]> cache = ThreadLocal.withInitial(() -> new char[32]);

    public static String getRandomString(int n) {
        if (n <= 0) throw new IllegalArgumentException("Args less than zero");
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int size = charArray.length;
        char[] chars = cache.get();
        for (int i = 0; i < n; i++) {
            chars[i] = charArray[random.nextInt(size)];
        }
        return new String(chars, 0, n);
    }
}
