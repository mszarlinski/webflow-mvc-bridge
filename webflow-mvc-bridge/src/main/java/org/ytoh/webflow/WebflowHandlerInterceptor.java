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
package org.ytoh.webflow;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.DefaultFlowUrlHandler;
import org.springframework.webflow.context.servlet.FlowUrlHandler;
import org.springframework.webflow.mvc.servlet.MvcExternalContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A {@link org.springframework.web.servlet.HandlerInterceptor} implementation initializing {@link ExternalContextHolder} for Spring MVC web requests.
 * This is needed to access variables in scopes managed by webflow.
 *
 * @see WebflowArgumentResolver
 *
 * @author: Martin [ytoh] Hvizdos (martin <dot> hvizdos <at> testile <dot> org)
 */
public class WebflowHandlerInterceptor extends HandlerInterceptorAdapter implements ServletContextAware {

//--------------------------------------------------------------------------------------------------------------------
// instance fields
//--------------------------------------------------------------------------------------------------------------------

    private ServletContext servletContext;
    private FlowUrlHandler flowUrlHandler = new DefaultFlowUrlHandler();

//--------------------------------------------------------------------------------------------------------------------
// public methods
//--------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ExternalContextHolder.setExternalContext(createServletExternalContext(request, response));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ExternalContextHolder.setExternalContext(null);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

//--------------------------------------------------------------------------------------------------------------------
// private methods
//--------------------------------------------------------------------------------------------------------------------
	
    private ExternalContext createServletExternalContext(HttpServletRequest request, HttpServletResponse response) {
        return new MvcExternalContext(servletContext, request, response, flowUrlHandler);
    }
	
//--------------------------------------------------------------------------------------------------------------------
// setters
//--------------------------------------------------------------------------------------------------------------------
	
    /**
     * @param flowUrlHandler - an optional {@link FlowUrlHandler} implementation.
     *
     * @see DefaultFlowUrlHandler - default implementation
     */
    public void setFlowUrlHandler(FlowUrlHandler flowUrlHandler) {
        this.flowUrlHandler = flowUrlHandler;
    }
}
