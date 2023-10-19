# warehouseAPI

REST api for warehouse with musical instruments.
#### Built with: 
Java, Jakarta EE, JAX-RS 
#### Tests:
JUnit 5, AssertJ
#### CI/CD:
Github actions, Maven, Docker hub

## All endpoints starts with
/api

## Product categories are 
GUITAR, BASS, DRUMS, KEYBOARD

## /products
#### GET
Returns all products.

#### POST
Add product.

body:
```JSON
[
    {
        "name": "Gibson Les Paul",
        "category": "GUITAR",
        "rating": "10"
    }
]
```
### /products/{id}
#### GET
Returns one product.

#### PUT
Modify one product.
body:
```JSON
[
    {
        "name": "Gibson Les Paul",
        "category": "GUITAR",
        "rating": "10"
    }
]
```

### /products/date?date=yy-mm-dd
#### GET
Returns all products after the date that is passed as query.

### /products/modified
#### GET
Returns all products that have been modified.

### /products/initials
#### GET
Returns all letters that have a product name starting with that letter, and how many products there is for each letter.

### /products/max-rating
#### GET
Returns all products with max rating, created within 30 days and sorted with new first.

## /categories
#### GET
Returns all categories with products.

### /categories/{category}
#### GET
Return all products in one category sorted alphabetical by product name.

### /categories/{category} /count
#### GET
Returns how many products there is in one category.



