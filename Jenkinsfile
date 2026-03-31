pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'rohinicc/ai-chatbot'
        DOCKER_TAG = "v${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build & Test') {
            steps {
                // Using Maven Wrapper
                bat './mvnw.cmd clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} -t ${DOCKER_IMAGE}:latest ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Requires docker credentials configured in Jenkins with ID 'dockerhub-credentials'
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                        bat "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                        bat "docker push ${DOCKER_IMAGE}:latest"
                    }
                }
            }
        }
    }
}
