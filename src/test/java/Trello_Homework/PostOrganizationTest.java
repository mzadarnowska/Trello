package Trello_Homework;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class PostOrganizationTest extends BaseTestTrello {


        private static String website;
        private static String name;
        private static String displayName;
        private static String orgId;
        private static String org2Id;
        private static String org3Id;



        @Test

        public void createNewOrganization(){

            displayName = "Organization";

            Response response = given()
                    .spec(reqSpec)
                    .queryParam("displayName", displayName)
                    .when()
                    .post(BASE_URL + ORGANIZATIONS)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();

            assertThat(json.getString("displayName")).isEqualTo("Organization");

            orgId = json.getString("id");

            given()
                    .spec(reqSpec)
                    .queryParam("id", orgId)
                    .when()
                    .delete(BASE_URL + ORGANIZATIONS + "/" + orgId)
                    .then()
                    .statusCode(HttpStatus.SC_OK);


        }
        @Test

        public void createOrganizationWithoutDisplayName(){
            Response response = given()
                    .spec(reqSpec)
                    .queryParam("displayName", "")
                    .when()
                    .post(BASE_URL + ORGANIZATIONS)
                    .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .response();

        }
        @Test

        public void nameWithLessThanThreeSigns(){


            name = "Tu";
            Response response = given()
                    .spec(reqSpec)
                    .queryParam("displayName",displayName)
                    .queryParam("name", name)
                    .when()
                    .post(BASE_URL + ORGANIZATIONS)
                    .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .response();
            JsonPath json = response.jsonPath();
            assertThat(json.getString("name")).isEqualTo(name);



        }
        @Test

        public void nameWithMoreThanThreeSigns() {
            displayName = "Organization";
            Response response = given()
                    .spec(reqSpec)
                    .queryParam("name", "Tam_Tu")
                    .queryParam("displayName",displayName)
                    .when()
                    .post(BASE_URL + ORGANIZATIONS)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            org2Id = json.getString("id");

            //delete organization

            given()
                    .spec(reqSpec)
                    .queryParam("id", org2Id)
                    .when()
                    .delete(BASE_URL + ORGANIZATIONS + "/" + org2Id)
                    .then()
                    .statusCode(HttpStatus.SC_OK);

        }

        @Test

        public void nameWithDescriptionAsInts() {

            Response response = given()
                    .spec(reqSpec)
                    .queryParam("displayName",displayName)
                    .queryParam("desc", 12345)
                    .when()
                    .post(BASE_URL + ORGANIZATIONS)
                    .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            assertThat(json.getString("desc")).isEqualTo(12345);
        }

        @Test

        public void websiteStartingWithHTTPP() {

            Response response = given()
                    .spec(reqSpec)
                    .queryParam("displayName", displayName)
                    .queryParam("website", "httpp://akademiaqa.pl")
                    .when()
                    .post(BASE_URL + ORGANIZATIONS)
                    .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            assertThat(json.getString("website")).startsWith("http://");
        }

        @Test

        public void websiteStartingWithHTTPS() {

            website = "https://www.akademiaqa.pl";
            displayName = "Organization";

            Response response = given()
                    .spec(reqSpec)
                    .queryParam("displayName", displayName)
                    .queryParam("website", website)
                    .when()
                    .post(BASE_URL + ORGANIZATIONS)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            assertThat(json.getString("website")).startsWith("https://");

            org3Id = json.getString("id");

            given()
                    .spec(reqSpec)
                    .queryParam("id", org3Id)
                    .when()
                    .delete(BASE_URL + ORGANIZATIONS + "/" + org3Id)
                    .then()
                    .statusCode(HttpStatus.SC_OK);
        }
    }
//pytania:
//1.czy zmienną należy podawać w każdym teście np. jak u mnie displayNmae raz podana, a raz nie. Jeden test przechodzi
//bez podanej zmiennej, a inny przechodzi dopiero po dodaniu zmiennej w teście
//2. czy response możemy od razu zamienić na JsonPath, jeśli tak to w jaki sposób, mi się nie udało tego zrobić,



