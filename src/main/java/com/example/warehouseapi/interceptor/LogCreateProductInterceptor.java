package com.example.warehouseapi.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@LogCreateProduct
public class LogCreateProductInterceptor {

    Logger logger = LoggerFactory.getLogger(LogCreateProductInterceptor.class);

    @AroundInvoke
    public Object LogCreateProduct(InvocationContext context) throws Exception {
        logger.info(Arrays.toString(context.getParameters()));
        return context.proceed();
    }
}
