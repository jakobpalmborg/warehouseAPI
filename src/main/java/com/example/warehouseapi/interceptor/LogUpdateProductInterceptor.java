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
@LogUpdateProduct
public class LogUpdateProductInterceptor {
    Logger logger = LoggerFactory.getLogger(LogUpdateProductInterceptor.class);

    @AroundInvoke
    public Object LogUpdateProduct(InvocationContext context) throws Exception {
        String arguments = Arrays.toString(context.getParameters());
        String argumentsWithEditedText = arguments.replace("NewProduct", "Updated to: ").replace("[", "").replace("]", "");
        logger.info("Product with id: " + argumentsWithEditedText);
        return context.proceed();
    }
}
