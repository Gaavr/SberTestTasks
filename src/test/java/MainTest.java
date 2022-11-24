import static io.restassured.RestAssured.*;

import config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import users.UserRequest;
import users.UserResponse;
import users.UserUpdatedResponse;

public class MainTest extends TestConfig {

    UserRequest userRequest = new UserRequest();
    UserResponse userResponse;
    UserUpdatedResponse userUpdatedResponse;
    private String id;

    @Test
    public void actionsWithUserTest() throws InterruptedException {
        userResponse = createUser();
        id = userResponse.getId();

        userUpdatedResponse = updateUser(id);
        Assert.assertEquals(userRequest.getName(), userUpdatedResponse.getName());
        Assert.assertEquals(userRequest.getJob(), userUpdatedResponse.getJob());

        //ендпоинт "api/user/{id}" не работает для новых юзеров (возвращается 404)
        //если бы все работало, по аналогии с UserUpdatedResponse создал бы dto и сравнил бы поля
        //отправляемые в реквесте с полями в респонсе
        userResponse = getUser(id);

        deleteUser(userResponse.getId());
    }

    public UserResponse createUser() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(userRequest)
                .log().uri()
                .when()
                .post();

        return response
                .then()
                .statusCode(201)
                .extract()
                .as(UserResponse.class);
    }
    public UserUpdatedResponse updateUser(String id) {
        userRequest.setName("new name");
        userRequest.setJob("new job");

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(userRequest)
                .log().uri()
                .when()
                .put("/" + id);

        return response
                .then()
                .statusCode(200)
                .extract().as(UserUpdatedResponse.class);
    }

    public UserResponse getUser(String id) {
        return given().when().get( "/" + id).then()
                .statusCode(200)
                .log().body()
                .extract().as(UserResponse.class);
    }

    public void deleteUser(String id) {
        given()
                .when()
                .delete("/" + id)
                .then()
                .log().body()
                .statusCode(204);
    }
}
