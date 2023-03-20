package stepDefinitions.api;

import hooks.api.HooksAPI;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import utilities.ConfigReader;


import static io.restassured.RestAssured.given;

public class CommonAPI {


    public static String fullPath;
    Response response;


    @Given("Api kullanicisi {string} path parametreleri set eder")
    public void api_kullanicisi_path_parametreleri_set_eder(String rawPaths) {
        
      //  spec.pathParams("pp1","api","pp2","login");
      //  Response response = given().when().get("{pp1}/{pp2}");
        
        String [] paths = rawPaths.split(",");
        StringBuilder tempPath = new StringBuilder("{");

        for (int i = 0; i < paths.length; i++) {
            String key = "pp" + i;
            String value = paths[i].trim();
            HooksAPI.spec.pathParam(key,value);
            
            tempPath.append(key + "}/{");
        }
        tempPath.deleteCharAt(tempPath.lastIndexOf("{"));
        tempPath.deleteCharAt(tempPath.lastIndexOf("/"));

        System.out.println("tempPath = " + tempPath);

        fullPath = tempPath.toString();
    }



    @Given("Api kullanicisi email ve password girer.")
    public void api_kullanicisi_email_ve_password_girer() {

        String email = ConfigReader.getProperty("email");
        String password = ConfigReader.getProperty("password");

        /*
        {
             "email": "admin@gmail.com",
              "password": "123123123"
        }
         */

        JSONObject reqBody = new JSONObject();

        reqBody.put("email",email);
        reqBody.put("password",password);

        response = given()
                .contentType(ContentType.JSON)
                .spec(HooksAPI.spec)
                .when()
                .body(reqBody.toString())
                .post(fullPath);

        response.prettyPrint();

    }

    @Given("Api kullanicisi response kaydeder")
    public void api_kullanicisi_response_kaydeder() {

        response = given()
                .headers("Authorization","Bearer " + HooksAPI.token)
                .contentType(ContentType.JSON)
                .spec(HooksAPI.spec)
                .when()
                .get(fullPath);

        response.prettyPrint();

    }
}
