package com.example.warehouseapi.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@LogMethodEntry
public class LogMethodEntryInterceptor {

    Logger logger = LoggerFactory.getLogger(LogMethodEntryInterceptor.class);

    @AroundInvoke
    public Object logMethodEntry(InvocationContext context) throws Exception {
        logger.info("Call to method: " + context.getMethod().getName() + "in class: " + context.getMethod().getDeclaringClass());
        return context.proceed();
    }
}
