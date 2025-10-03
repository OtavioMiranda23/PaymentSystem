package com.example.Transacoes;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
class AcceptanceTests {
	static String userId;
	static String anotherUserId;
	static String anotherUserAccountNumber;

	@BeforeAll
	static void createUsers() throws URISyntaxException, IOException, InterruptedException, JSONException {
		String bodyCreateUser = """
				{
					"name": "otavio"
				}
				""";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest createUserRequest = HttpRequest.newBuilder()
				.uri(new URI("http://localhost:8080/api/user"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(bodyCreateUser))
				.build();
		HttpResponse<String> responseCreateUser = client.send(createUserRequest, HttpResponse.BodyHandlers.ofString());
		String responseBodyUser = responseCreateUser.body();
		var jsonResponseUser = new JSONObject(responseBodyUser);
		String responseUserName = jsonResponseUser.getString("name");
		assertEquals("otavio", responseUserName);
		String responseUserAccount = jsonResponseUser.getString("accountNumber");
		assertEquals(10, responseUserAccount.length());
		String responseUserId = jsonResponseUser.getString("id");
		assertNotNull(responseUserId);
		userId = responseUserId;

		String bodyCreateAnotherUser = """
        {
            "name": "joao"
        }
        """;
		HttpRequest createAnotherUserRequest = HttpRequest.newBuilder()
				.uri(new URI("http://localhost:8080/api/user"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(bodyCreateAnotherUser))
				.build();
		HttpResponse<String> responseCreateAnotherUser = client.send(createAnotherUserRequest, HttpResponse.BodyHandlers.ofString());
		String responseBodyAnotherUser = responseCreateAnotherUser.body();
		var jsonResponseAnotherUser = new JSONObject(responseBodyAnotherUser);
		String responseAnotherUserName = jsonResponseAnotherUser.getString("name");
		assertEquals("joao", responseAnotherUserName);
		String responseAnotherUserAccount = jsonResponseAnotherUser.getString("accountNumber");
		anotherUserAccountNumber = responseAnotherUserAccount;
		assertEquals(10, responseAnotherUserAccount.length());
		String responseAnotherUserId = jsonResponseAnotherUser.getString("id");
		assertNotNull(responseAnotherUserId);
		anotherUserId = responseAnotherUserId;
	}
	@Test
	void createSuccessTransaction() throws URISyntaxException, IOException, InterruptedException, JSONException {
		HttpClient client = HttpClient.newHttpClient();
		LocalDateTime today = LocalDateTime.now();
		var toFulfilledAt = today.plusDays(1);
		String body = """
				{
					"recipientAccountNumber": "%s",
					"amount": "10.00",
					"toFulfillAt": "%s"
				}
				""".formatted(anotherUserAccountNumber, toFulfilledAt);
		URI uri = URI.create(String.format("http://localhost:8080/api/users/%s/transactions",
				userId));
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String responseBody = response.body();
		var jsonResponse = new JSONObject(responseBody);
		String responseId = jsonResponse.getString("id");
		assertNotNull(responseId);
		String responseSender = jsonResponse.getJSONObject("sender").getString("name");
		assertEquals("otavio", responseSender);
		String responseRecipient = jsonResponse.getJSONObject("recipient").getString("name");
		assertEquals("joao", responseRecipient);
		String responseAmount = jsonResponse.getString("amount");
		assertEquals(new BigDecimal("10.0"), new BigDecimal(responseAmount));
		String responseToFulfilledAt =  jsonResponse.getString("toFulfilledAt");
		assertEquals(toFulfilledAt, LocalDateTime.parse(responseToFulfilledAt));
		String responseCreatedAt =  jsonResponse.getString("createdAt");
		assertEquals(LocalDateTime.now().toString().split("T")[0], responseCreatedAt.split("T")[0]);
		String responseTaxedAmount = jsonResponse.getString("taxedAmount");
		assertEquals(new BigDecimal("13.325"), new BigDecimal(responseTaxedAmount));
	}
//
//	@Test
//	void getAllTransactions() throws URISyntaxException, IOException, InterruptedException, JSONException {
//		HttpClient client = HttpClient.newHttpClient();
//		URI uri = URI.create(String.format("http://localhost:8080/api/transaction/%s", this.userId));
//		HttpRequest request = HttpRequest.newBuilder()
//				.uri(uri)
//				.header("Content-Type", "application/json")
//				.GET()
//				.build();
//		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//		var responseJson = new JSONObject(response.body());
//		JSONArray transactions = responseJson.getJSONArray("transactions");
//		assertEquals(1, transactions.length());
//
//	}

	@Test
	void createInvalidTransaction() throws URISyntaxException, IOException, InterruptedException, JSONException {
		LocalDateTime toFulfilledAt = LocalDateTime.of(2025, Month.DECEMBER, 25, 10, 30, 0, 0);
		String body = """
				{
					"recipientAccountNumber": "%s",
					"amount": "30.00",
					"toFulfillAt": "%s"
				}
				""".formatted(anotherUserAccountNumber,toFulfilledAt);
		HttpClient client = HttpClient.newHttpClient();
		URI uri = URI.create(String.format("http://localhost:8080/api/users/%s/transactions",
				userId));
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		var responseJson = new JSONObject(response.body());
		String message = responseJson.getString("message");
		var expectedMessage = "Transferência não permitida, pois não foi encontrada data aplicável";
		assertEquals(expectedMessage,message);
	}
}