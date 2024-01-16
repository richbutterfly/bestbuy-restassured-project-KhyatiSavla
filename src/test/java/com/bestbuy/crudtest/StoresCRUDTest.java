package com.bestbuy.crudtest;

import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.StoresTestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class StoresCRUDTest extends StoresTestBase {

    static int storeId;
    static String name = "Primark";
    static String type = "Store";
    static String address = "123 High Road";
    static String address2 = "Wembley";
    static String city = "London";
    static String state = "UK";
    static String zip = "12345";
    static double lat = 56.58720;
    static double lng = 10.2644;
    static String hours = "Mon: 9-9; Tue: 9-9; Wed: 9-9; Thurs: 9-9; Fri: 9-9; Sat: 9-9; Sun: 11-4";

    @Test
    public void test01_verifyStoreCreatedSuccessfully() {
        HashMap<String, Object> services = new HashMap<>();
        services.put("name", "Repairs");
        services.put("id", "01");
        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);
        storePojo.setServices(services);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .body(storePojo)
                .post();
        response.prettyPrint();
        response.then().statusCode(201);

        storeId = response.then().extract().path("id");
        System.out.println("ID = " + storeId);
    }

    @Test
    public void test02_VerifyStoreReadSuccessfully() {

        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/" + storeId);
        response.then().statusCode(200);

        response.prettyPrint();

    }

    @Test
    public void test03_verifyStoreUpdateSuccessfully() {
        StorePojo storePojo = new StorePojo();
        storePojo.setName("Westfield");
        storePojo.setType(type + TestUtils.getRandomValue());
        storePojo.setCity("London");
        storePojo.setHours("Mon: 8-5; Tue: 8-5; Wed: 8-5; Thurs: 8-5; Fri: 8-5; Sat: 8-5; Sun: 8-5");

        Response response = given()
                .header("Content-Type", "application/json")
                .pathParams("id", storeId)
                .when()
                .body(storePojo)
                .patch("/{id}");
        response.then().log().body().statusCode(200);

    }

    @Test
    public void test04_VerifyStoreDeleteSuccessfully() {

        given()
                .when()
                .delete("/" + storeId)
                .then()
                .statusCode(200);

    }
}
