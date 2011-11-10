/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ytoh.webflow.test.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableList.of;

/**
 * @author: Martin [ytoh] Hvizdos (martin <dot> hvizdos <at> testile <dot> org)
 */
@Service
public class FriendService {

    private Map<String, List<String>> friends = ImmutableMap.<String, List<String>>of("Alice", of("Mary", "Jane", "John"),
                                                                                      "Bob",   of("Ben", "Brian", "Ryan"));

    public List<String> userFriends(String userName) {
        return friends.containsKey(userName) ? friends.get(userName) : Lists.<String>newArrayList();
    }
}
