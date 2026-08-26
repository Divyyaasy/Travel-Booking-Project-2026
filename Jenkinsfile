pipeline {

    agent any


    environment {
        IMAGE_NAME = "swapnilwaghmare/travel-booking-app"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/waghmareswapnil21/travel-booking-app.git'
            }
        }

        stage('Build') {
            steps {
                dir('travel-booking-app') {
            sh 'mvn clean package -DskipTests'
        }
            }
        }

        stage('Unit Test') {
            steps {
               dir('travel-booking-app') {
            sh 'mvn clean package -DskipTests'
        }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('travel-booking-app') {
            sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
        }
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                        credentialsId: 'docker',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS')]) {

                    sh '''
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                    docker push $IMAGE_NAME:$IMAGE_TAG
                    '''
                }
            }
            stage('Deploy to Kubernetes') {
    steps {
        dir('travel-booking-app') {
            sh '''
            kubectl apply -f k8s/deployment.yaml
            kubectl apply -f k8s/service.yaml
            kubectl port-forward --address 0.0.0.0 service/travel-booking-service 8081:8081
            '''
        }
    }
}
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully.'
        }

        failure {
            echo 'Pipeline failed.'
        }
    }
}
