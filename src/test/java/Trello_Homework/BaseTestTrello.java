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

        protected static final String KEY = "7e6606fb2518eab25ca36a4303affa62";
        protected static final String TOKEN = "58b045a9bb0cd922aea7482d193bee5099c2a352a2f2341950f2c5137f17aa88";

        protected static RequestSpecBuilder reqBuilder;
        protected static RequestSpecification reqSpec;

        @BeforeAll
        public static void beforeAll(){

            //KEY = System.getProperty("key");
            //TOKEN = System.getProperty("token");

            reqBuilder = new RequestSpecBuilder();
            reqBuilder.addQueryParam("key", KEY);
            reqBuilder.addQueryParam("token", TOKEN);
            reqBuilder.setContentType(ContentType.JSON);

            reqSpec = reqBuilder.build();

        }
    }




