package com.example.Transacoes;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
class AcceptanceTests {

	@Test
	void createSuccessTransaction() throws URISyntaxException, IOException, InterruptedException, JSONException {
		LocalDateTime today = LocalDateTime.now();
		var toFulfilledAt = today.plusDays(1);
		String body = """
				{
					"sender": "otavio@gmail.com",
					"recipient": "joao@gmail.com",
					"amount": "10.00",
					"toFulfillAt": "%s"
				}
				""".formatted(toFulfilledAt);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI("http://localhost:8080/api/transactions"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String responseBody = response.body();
		var jsonResponse = new JSONObject(responseBody);
		String responseId = jsonResponse.getString("id");
		assertNotNull(responseId);
	}

	@Test
	void createInvalidTransaction() throws URISyntaxException, IOException, InterruptedException, JSONException {
		LocalDateTime toFulfilledAt = LocalDateTime.of(2025, Month.DECEMBER, 25, 10, 30, 0, 0);
		String body = """
				{
					"sender": "otavio@gmail.com",
					"recipient": "joao@gmail.com",
					"amount": "30.00",
					"toFulfillAt": "%s"
				}
				""".formatted(toFulfilledAt);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI("http://localhost:8080/api/transactions"))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		var resonseJson = new JSONObject(response.body());
		String message = resonseJson.getString("message");
		System.out.println(message);
	}

}
