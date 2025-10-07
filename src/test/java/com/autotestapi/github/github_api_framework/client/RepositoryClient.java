package com.autotestapi.github.github_api_framework.client;

import com.autotestapi.github.github_api_framework.models.RepositoryModel;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RepositoryClient {
	
	//Dotenv dotenv = Dotenv.configure()
	//	    .directory("./") // Força a busca na raiz do diretório de trabalho
	//	    .filename(".env") // Especifica o nome
	//	    .load();

	private final String Base_URL =  "https://api.github.com";
	private final String REPO_PATH =  "/user/repos";
	
    // O nome do usuário autenticado é necessário para alguns endpoints (e.g., PUT, DELETE)
	private final String authenticatedUser; //dotenv.get("GITHUB_USERNAME");
	private final String token; //dotenv.get("GITHUB_TOKEN");
	
	
	public RepositoryClient(String authenticatedUser, String token) {
		super();
		this.authenticatedUser = System.getenv("GITHUB_USERNAME");
		this.token = System.getenv("GITHUB_TOKEN");
	}

	private RequestSpecification getBaseSpec() {
		return RestAssured.given()
				.baseUri(Base_URL)
				.header("Authorization", "token " + token)//system.getenv("GITHUB_TOKEN"))
				.header("Accept", "application/vnd.github.v3+json")
				.contentType("application/json");
				//.log().all();
	}
	
	//1. POST (Create)
	public Response createRepository(RepositoryModel payload) {
		return getBaseSpec()
				.body(payload)
				.when()
				.post(REPO_PATH)
				.prettyPeek();
	}
	
	//2. Get (Read One)
	public Response getRepository(String repoName) {
		return getBaseSpec()
				.pathParam("owner", authenticatedUser)
				.pathParam("repo", repoName)
				.when()
				.get("/repos/{owner}/{repo}")
				.prettyPeek();
	}
	
	//3. GET (Read All)
	public Response geAllRepositories() {
		return getBaseSpec()
				.queryParam("per_page", 100) // <-- ADICIONADA A PAGINAÇÃO
				.when()
				.get(REPO_PATH)
				.prettyPeek();
	}
	
	//4. PATCH (Update)
	public Response updateRepository(String repoName, RepositoryModel payload) {
		return getBaseSpec()
				.pathParam("owner", authenticatedUser)
				.pathParam("repo", repoName)
				.body(payload)
				.when()
				.patch("/repos/{owner}/{repo}")
				.prettyPeek();
	}
	
	//5. DELETE
	public Response deleteRepository(String repoName) {
		return getBaseSpec()
				.pathParam("owner", authenticatedUser)
				.pathParam("repo", repoName)
				.when()
				.delete("/repos/{owner}/{repo}")
				.prettyPeek();
	}
	
}







