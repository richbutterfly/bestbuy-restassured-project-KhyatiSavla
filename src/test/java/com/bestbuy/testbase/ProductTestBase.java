package com.bestbuy.testbase;

import com.bestbuy.propertyreader.PropertyReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class ProductTestBase {
    @BeforeClass
    public void inIt(){
        RestAssured.baseURI = PropertyReader.getInstance().getProperty("baseURI");
        RestAssured.port = 3030;
        RestAssured.basePath = PropertyReader.getInstance().getProperty("productPath");
    }

}
