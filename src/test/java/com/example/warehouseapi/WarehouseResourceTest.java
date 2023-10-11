
package com.example.warehouseapi;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.warehouseapi.entities.Category;
import com.example.warehouseapi.entities.ImmutableProduct;
import com.example.warehouseapi.entities.NewProduct;
import com.example.warehouseapi.entities.Product;
import com.example.warehouseapi.service.Iwarehouse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.MediaType;
import org.assertj.core.api.Assertions;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class WarehouseResourceTest {

    @Mock
    Iwarehouse mockWarehouse;

    Dispatcher dispatcher;

    @BeforeEach
    public void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        var warehouseResource = new WarehouseResource(mockWarehouse);
        dispatcher.getRegistry().addSingletonResource(warehouseResource);
    }

    @Test
    public void productsReturnsListOfProductsWithStatus200() throws Exception {
        Mockito.when(mockWarehouse.getAllProducts()).thenReturn(List.of(
                new ImmutableProduct("a23456789012345678901234567890123456", "Fender Stratocaster", Category.GUITAR, 8, "2023-10-10", "2023-10-10"),
                new ImmutableProduct("b23456789012345678901234567890123456", "Fender Jazz Bass", Category.BASS, 8, "2023-10-10", "2023-10-10")));

        MockHttpRequest request = MockHttpRequest.get("/products");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("[{\"id\":\"a23456789012345678901234567890123456\",\"name\":\"Fender Stratocaster\",\"category\":\"GUITAR\",\"rating\":8,\"creationDate\":\"2023-10-10\",\"modificationDate\":\"2023-10-10\"}," +
                        "{\"id\":\"b23456789012345678901234567890123456\",\"name\":\"Fender Jazz Bass\",\"category\":\"BASS\",\"rating\":8,\"creationDate\":\"2023-10-10\",\"modificationDate\":\"2023-10-10\"}]",
                response.getContentAsString());
    }

    @Test
    void postProduct() throws Exception {
        MockHttpRequest request = MockHttpRequest.post("/products");
        String json = new ObjectMapper().writeValueAsString(new ImmutableProduct("a23456789012345678901234567890123456", "Fender Stratocaster", Category.GUITAR, 8, "2023-10-10", "2023-10-10"));
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(400, response.getStatus());
    }

    @Test

    public void productsIdReturnsOptionalTrueAndStatus200() throws Exception {

        Optional<ImmutableProduct> optional = Optional.of((new ImmutableProduct("a23456789012345678901234567890123456", "Fender Stratocaster", Category.GUITAR, 8, "2023-10-10", "2023-10-10")));
        Mockito.when(mockWarehouse.getOneProduct("a23456789012345678901234567890123456"))
                .thenReturn(optional);

        MockHttpRequest request = MockHttpRequest.get("/products/a23456789012345678901234567890123456");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("{\"empty\":false,\"present\":true}", response.getContentAsString());
    }

    @Test
    public void categoriesReturnAllProductsInCategoryAndStatus200() throws Exception {
        Mockito.when(mockWarehouse.getCategory(Category.GUITAR)).thenReturn(List.of(
                new ImmutableProduct("a23456789012345678901234567890123456", "Fender Stratocaster", Category.GUITAR, 8, "2023-10-10", "2023-10-10"),
                new ImmutableProduct("b23456789012345678901234567890123456", "Gibson Les Paul", Category.GUITAR, 2, "2023-10-10", "2023-10-10")));

        MockHttpRequest request = MockHttpRequest.get("/categories/guitar");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("[{\"id\":\"a23456789012345678901234567890123456\",\"name\":\"Fender Stratocaster\",\"category\":\"GUITAR\",\"rating\":8,\"creationDate\":\"2023-10-10\",\"modificationDate\":\"2023-10-10\"}," +
                        "{\"id\":\"b23456789012345678901234567890123456\",\"name\":\"Gibson Les Paul\",\"category\":\"GUITAR\",\"rating\":2,\"creationDate\":\"2023-10-10\",\"modificationDate\":\"2023-10-10\"}]",
                response.getContentAsString());
    }

    @Test
    void putProductGetStatus202() throws Exception {
        MockHttpRequest request = MockHttpRequest.put("/products/a23456789012345678901234567890123456");
        String json = new ObjectMapper().writeValueAsString(new NewProduct("Fender Stratocaster", "Guitar", 8));
        request.content(json.getBytes());
        request.contentType(MediaType.APPLICATION_JSON);
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(202, response.getStatus());
    }


    @Test
    public void newProductsReturnProductsAndStatus200() throws Exception {
        String dateString = "2023-10-09";
        String dateStringWithHours = dateString + " 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateStringWithHours, formatter);
        Mockito.when(mockWarehouse.getNewProducts(dateTime)).thenReturn(List.of(
                new ImmutableProduct("a23456789012345678901234567890123456", "Fender Stratocaster", Category.GUITAR, 8, "2023-10-10", "2023-10-10"),
                new ImmutableProduct("b23456789012345678901234567890123456", "Gibson Les Paul", Category.GUITAR, 2, "2023-10-10", "2023-10-10")));

        MockHttpRequest request = MockHttpRequest.get("/products/date?date=2023-10-09");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("[{\"id\":\"a23456789012345678901234567890123456\",\"name\":\"Fender Stratocaster\",\"category\":\"GUITAR\",\"rating\":8,\"creationDate\":\"2023-10-10\",\"modificationDate\":\"2023-10-10\"}," +
                        "{\"id\":\"b23456789012345678901234567890123456\",\"name\":\"Gibson Les Paul\",\"category\":\"GUITAR\",\"rating\":2,\"creationDate\":\"2023-10-10\",\"modificationDate\":\"2023-10-10\"}]",
                response.getContentAsString());
    }

    @Test
    public void modifiedProductsReturnModifiedProductsAndStatus200() throws Exception {
        Mockito.when(mockWarehouse.getModifiedProducts()).thenReturn(List.of(
                new ImmutableProduct("a23456789012345678901234567890123456", "Fender Stratocaster", Category.GUITAR, 8, "2023-10-09", "2023-10-10"),
                new ImmutableProduct("b23456789012345678901234567890123456", "Gibson Les Paul", Category.GUITAR, 2, "2023-10-09", "2023-10-10")));

        MockHttpRequest request = MockHttpRequest.get("/products/modified");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("[{\"id\":\"a23456789012345678901234567890123456\",\"name\":\"Fender Stratocaster\",\"category\":\"GUITAR\",\"rating\":8,\"creationDate\":\"2023-10-09\",\"modificationDate\":\"2023-10-10\"}," +
                        "{\"id\":\"b23456789012345678901234567890123456\",\"name\":\"Gibson Les Paul\",\"category\":\"GUITAR\",\"rating\":2,\"creationDate\":\"2023-10-09\",\"modificationDate\":\"2023-10-10\"}]",
                response.getContentAsString());
    }

    @Test
    public void categoriesReturnAllCategoriesWithProductsAndStatus200() throws Exception {
        Mockito.when(mockWarehouse.getAllCategoriesWithProducts()).thenReturn(Set.of(Category.GUITAR, Category.BASS));

        MockHttpRequest request = MockHttpRequest.get("/categories");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());

        assertTrue(response.getContentAsString().equals("[\"BASS\",\"GUITAR\"]") || response.getContentAsString().equals("[\"GUITAR\",\"BASS\"]"));
    }

    @Test
    public void categoriesCountAndStatus200() throws Exception {
        Mockito.when(mockWarehouse.getNumberOfProductsInCategory(Category.GUITAR)).thenReturn(2);

        MockHttpRequest request = MockHttpRequest.get("/categories/guitar/count");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("2",
                response.getContentAsString());
    }

    @Test
    public void letterOfProductNamesAndStatus200() throws Exception {
        Mockito.when(mockWarehouse.getLetterOfProductNames()).thenReturn(Map.of("A",2,"B", 3 ));

        MockHttpRequest request = MockHttpRequest.get("/products/initials");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertTrue(response.getContentAsString().equals("{\"A\":2,\"B\":3}") || response.getContentAsString().equals("{\"B\":3,\"A\":2}"));
    }

    @Test
    public void maxRatingAndStatus200() throws Exception {
        Mockito.when(mockWarehouse.getMaxRating()).thenReturn(List.of(
                new ImmutableProduct("a23456789012345678901234567890123456", "Fender Stratocaster", Category.GUITAR, 10, "2023-10-10", "2023-10-10"),
                new ImmutableProduct("b23456789012345678901234567890123456", "Gibson Les Paul", Category.GUITAR, 10, "2023-10-10", "2023-10-10")));

        MockHttpRequest request = MockHttpRequest.get("/products/max-rating");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(200, response.getStatus());
        assertEquals("[{\"id\":\"a23456789012345678901234567890123456\",\"name\":\"Fender Stratocaster\",\"category\":\"GUITAR\",\"rating\":10,\"creationDate\":\"2023-10-10\",\"modificationDate\":\"2023-10-10\"}," +
                        "{\"id\":\"b23456789012345678901234567890123456\",\"name\":\"Gibson Les Paul\",\"category\":\"GUITAR\",\"rating\":10,\"creationDate\":\"2023-10-10\",\"modificationDate\":\"2023-10-10\"}]",
                response.getContentAsString());
    }


}
