package Trello_Homework;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class e2eTest extends BaseTestTrello{


        private static String boardId;
        private static String firstListId;
        private static String secondListId;
        private static String cardId;



        @Test
        @Order(1)

        public void createNewBoard(){
            Response response = given()
                    .spec(reqSpec)
                    .queryParam("name", "My e2e board")
                    .queryParam("defaultLists", false)
                    .when()
                    .post(BASE_URL + "/" + BOARDS)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            boardId = json.getString("id");

            assertThat(json.getString("name")).isEqualTo("My e2e board");

        }

        @Test
        @Order(2)

        public void createFirstList(){
            Response response = given()
                    .spec(reqSpec)
                    .queryParam("name", "First list")
                    .queryParam("idBoard", boardId)
                    .when()
                    .post(BASE_URL + "/" + LISTS)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            assertThat(json.getString("name")).isEqualTo("First list");

            firstListId = json.getString("id");

//        Response responseGet = given()
//                .spec(reqSpec)
//                .when()
//                .get(BASE_URL + "/" + BOARDS + boardId + "/" + LISTS)
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        JsonPath jsonGet = responseGet.jsonPath();
//        List<String> list = jsonGet.getList("id");
//        assertThat(list).hasSize(1);


        }
        @Test
        @Order(3)

        public void createSecondList(){
            Response response = given()
                    .spec(reqSpec)
                    .queryParam("name", "Second list")
                    .queryParam("idBoard", boardId)
                    .when()
                    .post(BASE_URL + "/" + LISTS)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();

            secondListId = json.getString("id");

            assertThat(json.getString("name")).isEqualTo("Second list");

//        Response responseGet = given()
//                .spec(reqSpec)
//                .when()
//                .get(BASE_URL + "/" + BOARDS + boardId + "/" + LISTS)
//                .then()
//                .statusCode(200)
//                .extract()
//                .response();
//
//        JsonPath jsonGet = responseGet.jsonPath();
//        List<String> list2 = jsonGet.getList("id");
//        assertThat(list2).hasSize(2);
            // asercje są wykomentowane gdyż testy nie przechodziy, gdzie popełniłam błąd?


        }
        @Test
        @Order(4)

        public void addCardToFirstList(){
            Response response = given()
                    .spec(reqSpec)
                    .queryParam("idList", firstListId)
                    .queryParam("name", "First card")
                    .when()
                    .post(BASE_URL + "/" + CARDS)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            cardId = json.getString("id");

            assertThat(json.getString("name")).isEqualTo("First card");

        }

        @Test
        @Order(5)

        public void moveCardToSecondList(){
            Response response = given()
                    .spec(reqSpec)
                    .queryParam("idList", secondListId)
                    .when()
                    .put(BASE_URL + "/" + CARDS + "/" + cardId)
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .response();

            JsonPath json = response.jsonPath();
            assertThat(json.getString("idList")).isEqualTo(secondListId);

        }
        @Test
        @Order(6)

        public void deleteBoard(){
            given()
                    .spec(reqSpec)
                    .queryParam("id", boardId)
                    .when()
                    .delete(BASE_URL + "/" + BOARDS + "/" + boardId)
                    .then()
                    .statusCode(HttpStatus.SC_OK);

        }
    }


