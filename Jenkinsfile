
```groovy
pipeline {

    agent any

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"

        AWS_REGION = 'ap-south-1'
        AWS_ACCOUNT_ID = '858688938415'
        ECR_REPOSITORY = 'travel-booking-app'
        EKS_CLUSTER = 'travel-booking-eks'

        IMAGE_TAG = "${BUILD_NUMBER}"
        IMAGE_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPOSITORY}:${IMAGE_TAG}"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Divyyaasy/Travel-Booking-Project-2026.git'
            }
        }

        stage('Build') {
            steps {
                sh '''
                    echo "===== JAVA ====="
                    which java
                    java -version

                    echo "===== JAVAC ====="
                    which javac
                    javac -version

                    echo "===== JAVA_HOME ====="
                    echo "$JAVA_HOME"

                    echo "===== MAVEN ====="
                    which mvn
                    mvn -version

                    echo "===== BUILD ====="
                    mvn clean package -DskipTests
                '''
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_URI} .'
            }
        }

        stage('Login to ECR') {
            steps {
                sh '''
                    aws ecr get-login-password --region ${AWS_REGION} |
                    docker login --username AWS --password-stdin \
                    ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                '''
            }
        }

        stage('Push Image to ECR') {
            steps {
                sh 'docker push ${IMAGE_URI}'
            }
        }

        stage('Configure EKS') {
            steps {
                sh '''
                    aws eks update-kubeconfig \
                    --region ${AWS_REGION} \
                    --name ${EKS_CLUSTER}
                '''
            }
        }

        stage('Deploy to EKS') {
            steps {
                sh '''
                    sed "s|IMAGE_PLACEHOLDER|${IMAGE_URI}|g" \
                    k8s/deployment.yaml > k8s/deployment-ci.yaml

                    kubectl apply -f k8s/deployment-ci.yaml
                    kubectl apply -f k8s/service.yaml

                    kubectl rollout status \
                    deployment/travel-booking-app \
                    --timeout=180s
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                    echo "===== PODS ====="
                    kubectl get pods -o wide

                    echo "===== SERVICE ====="
                    kubectl get service travel-booking-service

                    echo "===== DEPLOYMENT ====="
                    kubectl get deployment travel-booking-app
                '''
            }
        }
    }

    post {

        success {
            echo 'Travel Booking CI/CD pipeline completed successfully.'
        }

        failure {
            echo 'Travel Booking CI/CD pipeline failed.'
        }
    }
}
```
