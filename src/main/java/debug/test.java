package debug;

import io.restassured.RestAssured;
import io.restassured.internal.multipart.RestAssuredMultiPartEntity;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Response response = given().when().get("https://4b1838bd-a717-43b1-b9ab-44c02628326c.mock.pstmn.io/getReq");
		response.prettyPrint();
	}

}
