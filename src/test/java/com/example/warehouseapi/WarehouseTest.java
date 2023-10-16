package com.example.warehouseapi;

import com.example.warehouseapi.entities.Category;
import com.example.warehouseapi.entities.ImmutableProduct;
import com.example.warehouseapi.service.Warehouse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WarehouseTest {
    @Test
    void whenAddOneProductThenProductListIsNotEmpty() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 8);
        List<ImmutableProduct> listOfAllProducts = myWarehouse.getAllProducts();
        assertThat(listOfAllProducts)
                .as("When product is added, the Productlist should not be emtpy")
                .isNotEmpty();
    }

    @Test
    void whenModifyProductThenProductNameIsModified() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProductWithManualId("1", "Fender Stratocaster", Category.GUITAR, 8);
        myWarehouse.modifyProduct("1", "Gibson Les Paul", Category.GUITAR, 1);
        assertThat(myWarehouse.getOneProduct("1")).isNotEmpty().get().hasFieldOrPropertyWithValue("name", "Gibson Les Paul");
    }

    @Test
    void whenGetOneProductThenGetOneProduct() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProductWithManualId("1", "Fender Stratocaster", Category.GUITAR, 8);
        assertThat(myWarehouse.getOneProduct("1")).isNotEmpty();
    }

    @Test
    void whenGetAllProductsThenGetTheRightNumberOfProductsAdded() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 8);
        myWarehouse.addProduct("Fender Jazz bass", Category.BASS, 4);
        myWarehouse.addProduct("Gretch Catalina", Category.DRUMS, 9);
        myWarehouse.addProduct("Nord lead stage", Category.KEYBOARD, 2);
        assertThat(myWarehouse.getAllProducts().size())
                .isEqualTo(4);
    }

    @Test
    void getAllProductsInCategory() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 8);
        myWarehouse.addProduct("Gibson Les Paul", Category.GUITAR, 1);
        myWarehouse.addProduct("Fender Jazz bass", Category.BASS, 4);
        assertThat(myWarehouse.getCategory(Category.GUITAR).size())
                .isEqualTo(2);
    }

    @Test
    void getProductsInCategorySorted() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Ibanez AS200", Category.GUITAR, 7);
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 8);
        myWarehouse.addProduct("Gibson Les Paul", Category.GUITAR, 1);
        myWarehouse.addProduct("Fender Jazz bass", Category.BASS, 4);
        assertThat(myWarehouse.getCategory(Category.GUITAR).get(0).name())
                .startsWith("F");
    }

    @Test
    void whenAskForNewProductsThenGetOnlyNewProducts() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProductWithManualDate("Fender Stratocaster", Category.GUITAR, 8, LocalDateTime.now().minusDays(10));
        myWarehouse.addProductWithManualDate("Gibson Les Paul", Category.GUITAR, 3, LocalDateTime.now().minusDays(4));
        myWarehouse.addProductWithManualDate("Fender Jazz bass", Category.BASS, 4, LocalDateTime.now().minusDays(2));
        assertThat(myWarehouse.getNewProducts(LocalDateTime.now().minusDays(5))).size()
                .isEqualTo(2);
    }


    @Test
    void whenAddOneProductGetProductWithTheRightName() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProductWithManualDate("Gibson Les Paul", Category.GUITAR, 3, LocalDateTime.now().minusDays(4));
        myWarehouse.addProductWithManualDate("Fender Jazz bass", Category.BASS, 4, LocalDateTime.now().minusDays(2));
        assertThat(myWarehouse.getNewProducts(LocalDateTime.now().minusDays(3)).get(0).name())
                .contains("Fender");
    }

    @Test
    void whenModifyOneProductThenOnlyGetOneProduct() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProductWithManualCreationAndModificationDate("Gibson Les Paul", Category.GUITAR, 3, LocalDateTime.now().minusDays(1), LocalDateTime.now());
        myWarehouse.addProduct("Fender Jazz bass", Category.BASS, 4);
        assertThat(myWarehouse.getModifiedProducts().size())
                .isEqualTo(1);
    }

    @Test
    void whenGetCategoryWithProductsThenGetOnlyCategoryWithProducts() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 8);
        myWarehouse.addProduct("Gibson Les Paul", Category.GUITAR, 1);
        myWarehouse.addProduct("Fender Jazz bass", Category.BASS, 4);
        assertThat(myWarehouse.getAllCategoriesWithProducts())
                .contains(Category.GUITAR)
                .contains(Category.BASS)
                .doesNotContain(Category.DRUMS)
                .doesNotContain(Category.KEYBOARD);
    }

    @Test
    void howManyProductsInOneCategory() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 8);
        myWarehouse.addProduct("Gibson Les Paul", Category.GUITAR, 1);
        myWarehouse.addProduct("Fender Jazz bass", Category.BASS, 4);
        assertThat(myWarehouse.getNumberOfProductsInCategory(Category.GUITAR))
                .isEqualTo(2);
    }

    @Test
    void getAMapOfLetterAndNumber() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Ibanez AS200", Category.GUITAR, 7);
        assertThat(myWarehouse.getLetterOfProductNames())
                .containsKey("I")
                .containsValue(1);
    }

    @Test
    void getAllLettersAndHowManyOfEach() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 8);
        myWarehouse.addProduct("Gibson Les Paul", Category.GUITAR, 1);
        myWarehouse.addProduct("Fender Jazz bass", Category.BASS, 4);
        assertThat(myWarehouse.getLetterOfProductNames())
                .containsEntry("F", 2)
                .containsEntry("G", 1);
    }

    @Test
    void getLetterOfProductNamesToHandleEmptyString() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("", Category.GUITAR, 8);
        assertThat(myWarehouse.getLetterOfProductNames()).isEmpty();
    }

    @Test
    void getLetterOfProductNamesToHandleNull() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct(null, Category.GUITAR, 8);
        assertThat(myWarehouse.getLetterOfProductNames()).isNotNull();
    }

    @Test
    void whenAskForRating10ThenGetOnlyRating10() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 1);
        myWarehouse.addProduct("Gibson Les Paul", Category.GUITAR, 10);
        myWarehouse.addProduct("Fender Jazz bass", Category.BASS, 0);
        myWarehouse.addProduct("Gretch Catalina", Category.DRUMS, 10);
        assertThat(myWarehouse.getMaxRating()).extracting(ImmutableProduct::rating)
                .contains(10)
                .doesNotContain(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void whenAskForMaxRatingThenGetOnlyProductsWithin30Days() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 10);
        myWarehouse.addProductWithManualDate("Gibson Les Paul", Category.GUITAR, 10, LocalDateTime.now().minusMonths(2));
        myWarehouse.addProductWithManualDate("Fender Jazz bass", Category.BASS, 10, LocalDateTime.now().minusDays(31));
        assertThat(myWarehouse.getMaxRating()).size()
                .isEqualTo(1);
    }

    @Test
    void whenGetMaxRatingThenSortedResultAfterCreationDate() {
        Warehouse myWarehouse = new Warehouse();
        myWarehouse.addProductWithManualDate("Gibson Les Paul", Category.GUITAR, 10, LocalDateTime.now().minusMonths(7));
        myWarehouse.addProduct("Fender Stratocaster", Category.GUITAR, 10);
        myWarehouse.addProductWithManualDate("Fender Jazz bass", Category.BASS, 10, LocalDateTime.now().minusDays(2));
        assertThat(myWarehouse.getMaxRating()).extracting(ImmutableProduct::creationDate)
                .isSortedAccordingTo(Comparator.naturalOrder());
    }
}
