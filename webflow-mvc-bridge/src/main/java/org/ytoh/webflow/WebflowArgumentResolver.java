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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.webflow.context.servlet.DefaultFlowUrlHandler;
import org.springframework.webflow.context.servlet.FlowUrlHandler;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.FlowExecutionKey;
import org.springframework.webflow.execution.repository.FlowExecutionRepository;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.executor.FlowExecutorImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.collect.Maps.filterValues;

/**
 * An implementation of the {@link WebArgumentResolver} that resolved arguments from webflow's Flow scope.
 *
 * <p>This implementation resolves Spring MVC controller method parameters annotated with @{@link Flow} annotation.</p>
 *
 * <p><strong>NOTE:</strong> This implementation accesses the {@link org.springframework.webflow.execution.repository.FlowExecutionRepository}
 * to retrieve the current flow execution to retrieve arguments from. This requires that the {@link org.springframework.webflow.context.ExternalContextHolder}
 * be correctly initialized for the current thread. This is done by a custom {@link org.springframework.web.servlet.HandlerInterceptor}.</p>
 *
 * @see WebflowHandlerInterceptor
 *
 * @author: Martin [ytoh] Hvizdos (martin <dot> hvizdos <at> testile <dot> org)
 */
public class WebflowArgumentResolver implements WebArgumentResolver, InitializingBean {

//--------------------------------------------------------------------------------------------------------------------
// instance fields
//--------------------------------------------------------------------------------------------------------------------

    private FlowUrlHandler flowUrlHandler = new DefaultFlowUrlHandler();
    private FlowExecutor flowExecutor;
    private FlowExecutionRepository executionRepository;

//--------------------------------------------------------------------------------------------------------------------
// public methods
//--------------------------------------------------------------------------------------------------------------------
	
    @Override
    public void afterPropertiesSet() throws Exception {
        executionRepository = ((FlowExecutorImpl) flowExecutor).getExecutionRepository();
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        if(isFlowParameter(methodParameter)) {
            return resolveFlowArgument(methodParameter, webRequest);
        }

        return UNRESOLVED;
    }
	
//--------------------------------------------------------------------------------------------------------------------
// private methods
//--------------------------------------------------------------------------------------------------------------------

    private boolean isFlowParameter(MethodParameter methodParameter) {
        return getParameterAnnotation(methodParameter) != null;
    }

    private Flow getParameterAnnotation(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(Flow.class);
    }

    private Object resolveFlowArgument(MethodParameter methodParameter, NativeWebRequest webRequest) {
        FlowExecution flowExecution = getCurrentFlowExecution((HttpServletRequest) webRequest.getNativeRequest());

        Flow parameterAnnotation = getParameterAnnotation(methodParameter);
        if("".equals(parameterAnnotation.value())) {
            return resolveByType(methodParameter, flowExecution);
        } else {
            return resolveByName(methodParameter, parameterAnnotation.value(), flowExecution);
        }
    }

    private Object resolveByName(MethodParameter methodParameter, String name, FlowExecution flowExecution) {
        MutableAttributeMap flowAttributes = flowExecution.getActiveSession().getScope();
        checkState(flowAttributes.contains(name, methodParameter.getParameterType()), "No object with name: [" + name + "] and of type: [" + methodParameter.getParameterType() + "] registered in flow scope");
        return flowAttributes.get(name);
    }

    private Object resolveByType(MethodParameter methodParameter, FlowExecution flowExecution) {
        Map flowAttributes = filterValues(flowExecution.getActiveSession().getScope().asMap(), instanceOf(methodParameter.getParameterType()));
        checkState(!flowAttributes.isEmpty(), "No object of type: [" + methodParameter.getParameterType() + "] registered in flow scope");
        checkState(flowAttributes.size() == 1, "More then one object of type: [" + methodParameter.getParameterType() + "] registered in flow scope");
        return ((Map.Entry) flowAttributes.entrySet().iterator().next()).getValue();
    }
	
	private FlowExecution getCurrentFlowExecution(HttpServletRequest request) {
		if(flowUrlHandler.getFlowExecutionKey(request) != null) {
            return getFlowExecution(executionRepository, request);
        } else {
            throw new IllegalArgumentException("No flow execution key found in request");
        }
	}
	
	private FlowExecution getFlowExecution(FlowExecutionRepository executionRepository, HttpServletRequest request) {
        String flowExecutionKeyParameter = flowUrlHandler.getFlowExecutionKey(request);
        FlowExecutionKey executionKey = executionRepository.parseFlowExecutionKey(flowExecutionKeyParameter);
        return executionRepository.getFlowExecution(executionKey);
    }
	
//--------------------------------------------------------------------------------------------------------------------
// setters
//--------------------------------------------------------------------------------------------------------------------
	
    @Required
    public void setFlowExecutor(FlowExecutor flowExecutor) {
        this.flowExecutor = flowExecutor;
    }

    /**
     * @param flowUrlHandler - an optional {@link FlowUrlHandler} implementation.
     *
     * @see DefaultFlowUrlHandler - default implementation
     */
    public void setFlowUrlHandler(FlowUrlHandler flowUrlHandler) {
        this.flowUrlHandler = flowUrlHandler;
    }
}
