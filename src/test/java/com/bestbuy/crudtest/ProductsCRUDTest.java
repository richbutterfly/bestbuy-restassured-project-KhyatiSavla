package com.bestbuy.crudtest;

import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.ProductTestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductsCRUDTest extends ProductTestBase {

    static String name = "Dell" + TestUtils.getRandomValue();
    static String type = "laptop";
    static double price = 2999.99;
    static int shipping = 0;
    static String upc = TestUtils.getRandomValue();
    static String description = "Latest Model";
    static String manufacturer = "Dell Inc.";
    static String model = "PQR123";
    static String url = "https://pisces.bbystatic.com/" + TestUtils.getRandomValue() + ".jpg;maxHeight=640;maxWidth=550";
    static String image = TestUtils.getRandomValue();
    static int productId;

    @Test
    public void test01_verifyProductCreatedSuccessfully() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .body(productPojo)
                .post();
        response.prettyPrint();
        response.then().statusCode(201);
        productId = response.then().extract().path("id");
        System.out.println("id = " + productId);

    }

    @Test
    public void test02_VerifyProductReadSuccessfully() {

        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/" + productId);
        response.then().statusCode(200);
        response.prettyPrint();

    }
    @Test
    public void test03_verifyProductUpdateSuccessfully() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("Iphone");
        productPojo.setType("Mobile");
        productPojo.setDescription("Oldest Model");
        productPojo.setManufacturer("Apple");

        Response response = given()
                .header("Content-Type", "application/json")
                .pathParams("id", productId)
                .when()
                .body(productPojo)
                .patch("/{id}");
        response.then().log().body().statusCode(200);

    }
    @Test
    public void test04_VerifyProductDeleteSuccessfully() {

        given()
                .when()
                .delete("/" + productId)
                .then()
                .statusCode(200);

    }
}
