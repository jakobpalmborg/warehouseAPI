
package com.example.warehouseapi;

import static org.junit.jupiter.api.Assertions.*;

import com.example.warehouseapi.entities.Category;
import com.example.warehouseapi.entities.ImmutableProduct;
import com.example.warehouseapi.service.Iwarehouse;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.spi.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class WarehouseResourceTest {

    @Mock
    Iwarehouse warehouse;

    Dispatcher dispatcher;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        var warehouseResource = new WarehouseResource(warehouse);
        dispatcher.getRegistry().addSingletonResource(warehouseResource);
    }

    @Test
    public void productsReturnsListOfProductsWithStatus200() throws Exception {
        Mockito.when(warehouse.getAllProducts()).thenReturn(List.of(
                new ImmutableProduct("a23456789012345678901234567890123456","Fender Stratocaster", Category.GUITAR, 8, "2023-10-10","2023-10-10" ),
                new ImmutableProduct("b23456789012345678901234567890123456","Fender Jazz Bass", Category.BASS, 8, "2023-10-10","2023-10-10" )));

        MockHttpRequest request = MockHttpRequest.get("/products");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals(List.of(
                new ImmutableProduct("a23456789012345678901234567890123456","Fender Stratocaster", Category.GUITAR, 8, "2023-10-10","2023-10-10" ),
                new ImmutableProduct("b23456789012345678901234567890123456","Fender Jazz Bass", Category.BASS, 8, "2023-10-10","2023-10-10" ).toString()),
                response.getContentAsString());
    }
}
