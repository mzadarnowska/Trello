package Trello_Homework;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;


    public class BaseTestTrello {

        protected static final String BASE_URL = "https://api.trello.com/1/";
        protected static final String ORGANIZATIONS = "organizations";
        protected static final String BOARDS = "boards";
        protected static final String LISTS = "lists";
        protected static final String CARDS = "cards";

        protected static String KEY;
        protected static String TOKEN;

        protected static RequestSpecBuilder reqBuilder;
        protected static RequestSpecification reqSpec;

        @BeforeAll
        public static void beforeAll(){

            KEY = System.getProperty("key");
            TOKEN = System.getProperty("token");

            reqBuilder = new RequestSpecBuilder();
            reqBuilder.addQueryParam("key", KEY);
            reqBuilder.addQueryParam("token", TOKEN);
            reqBuilder.setContentType(ContentType.JSON);

            reqSpec = reqBuilder.build();

        }
    }




