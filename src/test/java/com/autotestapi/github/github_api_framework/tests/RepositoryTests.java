package com.autotestapi.github.github_api_framework.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.autotestapi.github.github_api_framework.client.RepositoryClient;
import com.autotestapi.github.github_api_framework.models.RepositoryModel;

import io.restassured.response.Response;
import util.TestUtils;

public class RepositoryTests {

	private RepositoryClient repoClient;
	private String createdRepoName;

	@BeforeClass
	public void setup() {
		repoClient = new RepositoryClient();
	}

	//@Test(priority = 1)
	public void testPostCreateRepository() {
		createdRepoName = TestUtils.generateUniqueRepoName();

		// 1. PREPARAR: Criar o payload
		RepositoryModel payload = new RepositoryModel();
		payload.setName(createdRepoName);
		payload.setDescription("Repositório de teste criado via Rest Assured");
		payload.setPrivate(true);

		// 2. EXECUTAR: Chamar o cliente
		Response response = repoClient.createRepository(payload);

		// 3. VALIDAR: Asserções
		response.then().statusCode(201); // 201 Created

		// Mapear a resposta JSON de volta para o POJO e validar o nome
		RepositoryModel responseModel = response.as(RepositoryModel.class);
		Assert.assertEquals(responseModel.getName(), createdRepoName,
				"O nome do repositório deve corresponder ao enviado.");
		Assert.assertTrue(responseModel.isPrivate(), "O repositório deve ser privado.");
	}

	//@Test(priority = 2)
    @Test(dependsOnMethods = {"testPostCreateRepository"})

	public void testGetSingleRepository() {
		// 1. PREPARAR: Usar o nome do repositório criado no teste anterior
		Assert.assertNotNull(createdRepoName, "O repositório deve ter sido criado.");

		// 2. EXECUTAR: Chamar o cliente
		Response response = repoClient.getRepository(createdRepoName);

		// 3. VALIDAR: Asserções
		response.then().statusCode(200); // 200 OK

		// Validação do conteúdo: Verificar se o nome está correto
		response.then().body("name", org.hamcrest.Matchers.equalTo(createdRepoName));
	}

	//@Test(priority = 3)
    @Test(dependsOnMethods = {"testGetSingleRepository"})

	public void testPutUpdateRepository() {
		// 1. PREPARAR: Novo payload para atualização
		String newDescription = "Descrição atualizada via TestNG!";
		RepositoryModel updatePayload = new RepositoryModel();
		updatePayload.setName(createdRepoName); // <-- ADICIONADO: Nome do repositório é necessário!
		updatePayload.setDescription(newDescription);

		// 2. EXECUTAR: Chamar o cliente para autalizar
		// (Nota: GitHub usa PATCH para update de repositório, não PUT)
		Response response = repoClient.updateRepository(createdRepoName, updatePayload);

		// 3. VALIDAR: Asserções
		response.then().statusCode(200); // 200 OK
		response.then().body("description", org.hamcrest.Matchers.equalTo(newDescription));
	}

	//@Test(priority = 4)
    @Test(dependsOnMethods = {"testPostCreateRepository"})
	public void testGetRepositoriesListContainsCreatedRepo() throws InterruptedException {
		// 1. EXECUTAR: Chamar o endpoint para Listar todos os repositórios
		Response response = repoClient.geAllRepositories();

		// 2. VALIDAR: Asserções
		response.then().statusCode(200); // 200 OK

		// Validação de conteúdo: Verificar se o repositório criado está na lista
		response.then().body("name", org.hamcrest.Matchers.hasItem(createdRepoName));
	}

	//@Test(priority = 5)
    @Test(dependsOnMethods = {"testPutUpdateRepository"})

	public void testDeleteRepository() throws InterruptedException {
		// **********************************************
		// SOLUÇÃO: Pausa para permitir que a operação anterior finalize
		// **********************************************
		System.out.println("Aguardando 5 segundos para garantir que a operação anterior do GitHub foi concluída...");
		Thread.sleep(5000); // Pausa de 5 segundos (5000 ms)

		// PASSO DE VERIFICAÇÃO CRÍTICO:
		// 1. Garanta que o repositório ainda existe antes de tentar deletar
		Response preDeleteCheck = repoClient.getRepository(createdRepoName);
		preDeleteCheck.then().statusCode(200); // <-- Esta asserção deve PASSAR

		// 2. EXECUTAR: Chamar o cliente para execução
		Response deleteResponse = repoClient.deleteRepository(createdRepoName);// createdRepoName);
		deleteResponse.prettyPeek();

		// 3. VALIDAR: Asserção de exclusão
		deleteResponse.then().statusCode(204);

		// 4. VERIFICAR: Tentar buscar o repositório excluído
		Response getResponse = repoClient.getRepository(createdRepoName);
		getResponse.then().statusCode(404); // 404 Not Found (Confirmação)
	}
}
