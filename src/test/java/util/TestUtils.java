package util;

import java.util.UUID;

public class TestUtils {
	
    /**
     * Gera um nome de repositório único para evitar colisões no GitHub.
     */
	public static String generateUniqueRepoName() {
		return "temp-repo-" + UUID.randomUUID().toString().substring(0, 8);
	}

}
