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
package org.ytoh.webflow.test;

import java.io.Serializable;

/**
 * @author: Martin [ytoh] Hvizdos (martin <dot> hvizdos <at> testile <dot> org)
 */
public class Account implements Serializable {
    private String userName;

    private String bestFriend;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBestFriend() {
        return bestFriend;
    }

    public void setBestFriend(String bestFriend) {
        this.bestFriend = bestFriend;
    }
}
