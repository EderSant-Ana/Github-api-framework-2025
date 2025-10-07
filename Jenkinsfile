pipeline {
    agent any

    environment {
        // Variáveis do ambiente Jenkins
	    // Mantemos só nome da variável pública; o valor será lido com withCredentials
	    GITHUB_USERNAME = "${env.GITHUB_USERNAME}"
    }

    stages {

        stage('Cleanup Docker') {
            steps {
                echo 'Limpando containers e imagens antigas...'
                sh '''
                    docker rm -f api-container || true
                    docker container prune -f --force || true
                    docker image prune -f || true
                '''
            }
        }

        stage('Checkout Code') {
            steps {
                echo 'Limpando workspace e clonando repositório...'
                deleteDir()  // remove todos os arquivos do workspace

                sh """
                    git clone -b main https://${env.GITHUB_TOKEN}@github.com/EderSant-Ana/Github-api-framework-2025.git .
                """
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Construindo imagem Docker...'
                sh 'docker build -t api-tests-image .'
            }
        }

        stage('Run API Tests') {
	        steps {
	            echo 'Executando testes automatizados de API (token passado de forma segura)...'
	            withCredentials([string(credentialsId: 'GITHUB_TOKEN_CRED', variable: 'GITHUB_TOKEN')]) {
	                // Para evitar problemas com caracteres no token, passamos via --env-file temporário.
	                sh '''
	                    set -e
	                    echo "GITHUB_TOKEN=${GITHUB_TOKEN}" > .env_github
	                    echo "GITHUB_USERNAME=${GITHUB_USERNAME}" >> .env_github
	                    # imprime apenas comprimento do token para debug (não mostra o token)
	                    echo "token length: $(wc -c < .env_github | awk '{print $1}')"
	                    docker run --name api-container --env-file .env_github api-tests-image
	                    rm -f .env_github
	                '''
	            }
	        }
        }

        stage('Publish Reports') {
            steps {
                echo 'Publicando relatórios JUnit...'
                sh '''
                    if docker ps -a --format '{{.Names}}' | grep -q '^api-container$'; then
                        docker cp api-container:/app/target/surefire-reports ./
                        docker rm -f api-container
                    else
                        echo "Container api-container não encontrado — pulando cópia de relatórios."
                    fi
                '''
                junit 'surefire-reports/*.xml'
                archiveArtifacts artifacts: 'surefire-reports/**/*', fingerprint: true
            }
        }
    }

    post {
        always {
            echo 'Limpando ambiente pós-build...'
            sh '''
                docker rm -f api-container || true
                docker container prune -f --force || true
                docker image prune -f || true
            '''
        }
    }
}
