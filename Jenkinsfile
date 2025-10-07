pipeline {
    agent any

    environment {
        // Variáveis do ambiente Jenkins
        GITHUB_TOKEN = "${env.GITHUB_TOKEN}"
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
                echo 'Executando testes automatizados de API...'
                sh """
                    docker run --name api-container \\
                    -e GITHUB_TOKEN=${env.GITHUB_TOKEN} \\
                    -e GITHUB_USERNAME=${env.GITHUB_USERNAME} \\
                    api-tests-image
                """
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
