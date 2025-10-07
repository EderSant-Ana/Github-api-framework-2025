pipeline {
    agent any

    stages {

        stage('Cleanup Docker') {
            steps {
                echo 'Limpando containers e imagens antigas...'
                sh '''
                    /usr/bin/docker rm -f api-container || true
                    /usr/bin/docker container prune -f || true
                    /usr/bin/docker image prune -f || true
                '''
            }
        }

        stage('Checkout Code') {
            steps {
                echo 'Fazendo checkout do repositório usando GITHUB_TOKEN...'
                sh '''
                    git config --global credential.helper store
                    echo "https://${GITHUB_TOKEN}:@github.com" > ~/.git-credentials
                    git clone -b main https://github.com/EderSant-Ana/Github-api-framework-2025.git .
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Construindo imagem Docker...'
                sh '/usr/bin/docker build -t api-tests-image .'
            }
        }

        stage('Run API Tests') {
            steps {
                echo 'Executando testes automatizados de API...'
                sh """
                    /usr/bin/docker run --name api-container \\
                    -e GITHUB_TOKEN=${GITHUB_TOKEN} \\
                    -e GITHUB_USERNAME=${GITHUB_USERNAME} \\
                    api-tests-image
                """
            }
        }

        stage('Publish Reports') {
            steps {
                echo 'Publicando relatórios JUnit...'
                sh '''
                    if /usr/bin/docker ps -a --format '{{.Names}}' | grep -q '^api-container$'; then
                        /usr/bin/docker cp api-container:/app/target/surefire-reports ./
                        /usr/bin/docker rm -f api-container
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
                /usr/bin/docker rm -f api-container || true
                /usr/bin/docker container prune -f || true
                /usr/bin/docker image prune -f || true
            '''
        }
    }
}
