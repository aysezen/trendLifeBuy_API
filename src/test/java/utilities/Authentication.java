package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Authentication {

    private static RequestSpecification spec;
    public static String generateToken(){

        spec = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("base_url")).build();

        spec.pathParams("pp1","api","pp2","login");

        Map<String,Object> dataCredentials = new HashMap<>();

        dataCredentials.put("email",ConfigReader.getProperty("email"));
        dataCredentials.put("password",ConfigReader.getProperty("password"));

        Response response = given()
                .contentType(ContentType.JSON)
                .spec(spec)
                .when()
                .body(dataCredentials)
                .post("{pp1}/{pp2}");

        response.prettyPrint();
        JsonPath jsonResponse = response.jsonPath();

        String token = jsonResponse.getString("token");

        return token;
    }

}
