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
package org.ytoh.webflow.test.controller;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ytoh.webflow.Flow;
import org.ytoh.webflow.test.Account;
import org.ytoh.webflow.test.service.FriendService;

import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.startsWithIgnoreCase;

/**
 * @author: Martin [ytoh] Hvizdos (martin <dot> hvizdos <at> testile <dot> org)
 */
@Controller
public class FriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping("/listMyFiends")
    public @ResponseBody List<String> listFriends(@Flow Account userAccount, @RequestParam("term") final String query) {
        return newArrayList(filter(friendService.userFriends(userAccount.getUserName()), new Predicate<String>() {

            @Override
            public boolean apply(String input) {
                return startsWithIgnoreCase(input, query);
            }
        }));
    }
}
