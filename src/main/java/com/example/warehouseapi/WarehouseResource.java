package com.example.warehouseapi;

import com.example.warehouseapi.entities.Category;
import com.example.warehouseapi.entities.ImmutableProduct;
import com.example.warehouseapi.entities.NewProduct;
import com.example.warehouseapi.service.Iwarehouse;
import com.example.warehouseapi.service.Warehouse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/")
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
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProduct(NewProduct prod) {
        String name = prod.name();
        Category category = prod.category();
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
}