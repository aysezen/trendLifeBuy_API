package stepDefinitions.api;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

import static io.restassured.RestAssured.given;

public class CommonAPI {

    public static RequestSpecification spec;

    @Before
    public void setUpApi(){
        spec = new RequestSpecBuilder().setBaseUri(ConfigReader.getProperty("base_url")).build();
    }



    @Given("Api kullanicisi {string} path parametreleri set eder")
    public void api_kullanicisi_path_parametreleri_set_eder(String rawPaths) {
        
      //  spec.pathParams("pp1","api","pp2","login");
      //  Response response = given().when().get("{pp1}/{pp2}");
        
        
        
        
        
        String [] paths = rawPaths.split(",");
        StringBuilder tempPath = new StringBuilder("{");

        for (int i = 0; i < paths.length; i++) {
            String key = "pp" + i;
            String value = paths[i].trim();
            spec.pathParam(key,value);
            
            tempPath.append(key + "}/{");
        }

        System.out.println("tempPath = " + tempPath);



    }
}
