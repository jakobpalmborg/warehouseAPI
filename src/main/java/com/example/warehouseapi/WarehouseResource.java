package com.example.warehouseapi;

import com.example.warehouseapi.entities.Category;
import com.example.warehouseapi.entities.ImmutableProduct;
import com.example.warehouseapi.entities.NewProduct;
import com.example.warehouseapi.interceptor.LogMethodEntry;
import com.example.warehouseapi.interceptor.LogCreateProduct;
import com.example.warehouseapi.interceptor.LogUpdateProduct;
import com.example.warehouseapi.service.Iwarehouse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Path("/")
@LogMethodEntry
public class WarehouseResource {

    private Iwarehouse warehouse;


    public WarehouseResource() {
    }

    @Inject
    public WarehouseResource(Iwarehouse warehouse) {
        this.warehouse = warehouse;
    }

    @POST
    @Path("/products")
    @LogCreateProduct
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProduct(@Valid NewProduct prod) {
        String name = prod.name();
        String categoryAsString = prod.category().toUpperCase();
        Category category = Category.valueOf(categoryAsString);
        int rating = prod.rating();
        warehouse.addProduct(name, category, rating);
        return Response.accepted().build();
    }

    @GET
    @Path("/products")

    @Produces(MediaType.APPLICATION_JSON)
    public List<ImmutableProduct> getAllProductsEndpoint() {
        return warehouse.getAllProducts();
    }

    @GET
    @Path("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<ImmutableProduct> getOneProductEndpoint(@PathParam("id") String id) {
        return warehouse.getOneProduct(id);
    }

    @GET
    @Path("/categories/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImmutableProduct> getAllProductsInCategory(@PathParam("category") Category category) {
        return warehouse.getCategory(category);
    }

    @PUT
    @Path("products/{id}")
    @LogUpdateProduct
    public Response modifyProductEndpoint(@PathParam("id") String id, NewProduct prod){
        String name = prod.name();
        String categoryAsString = prod.category().toUpperCase();
        Category category = Category.valueOf(categoryAsString);
        int rating = prod.rating();
        warehouse.modifyProduct(id, name, category, rating);
        return Response.accepted().build();
    }

    @GET
    @Path("/products/date")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImmutableProduct> getNewProductsEndpoint(@QueryParam("date") String dateString) {
        String dateStringWithHours = dateString + " 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateStringWithHours, formatter);
        return warehouse.getNewProducts(dateTime);
    }

    @GET
    @Path("/products/modified")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImmutableProduct> getModifiedProductsEndpoint() {
        return warehouse.getModifiedProducts();
    }

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Category> getAllCategoriesWithProductsEndpoint() {
        return warehouse.getAllCategoriesWithProducts();
    }

    @GET
    @Path("/categories/{category}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public int getNumberOfProductsInCategoryEndPoint(@PathParam("category") Category category) {
        return warehouse.getNumberOfProductsInCategory(category);
    }

    @GET
    @Path("/products/initials")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> getLetterOfProductNamesEndpoint() {
        return warehouse.getLetterOfProductNames();
    }

    @GET
    @Path("/products/max-rating")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ImmutableProduct> getMaxRatingEndpoint() {
        return warehouse.getMaxRating();
    }

}