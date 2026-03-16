pipeline {
    agent any

    environment {
        REGISTRY    = 'localhost:5000'
        IMAGE_NAME  = 'contact-service'
        IMAGE_TAG   = "${BUILD_NUMBER}"
        INFRA_REPO  = 'git@github.com:ivgallo/app-infra.git'
    }

    stages {

        stage('Checkout App') {
            steps {
                dir('app') {
                    git branch: 'main',
                        credentialsId: 'github-ssh',
                        url: 'git@github.com:ivgallo/contact-service.git'
                }
            }
        }

        stage('Checkout Infra') {
            steps {
                dir('infra') {
                    git branch: 'main',
                        credentialsId: 'github-ssh',
                        url: "${INFRA_REPO}"
                }
            }
        }

        stage('Build & Push Image') {
            steps {
                dir('app') {
                    sh """
                        docker build -t ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} .
                        docker tag ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG} ${REGISTRY}/${IMAGE_NAME}:latest
                        docker push ${REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}
                        docker push ${REGISTRY}/${IMAGE_NAME}:latest
                    """
                }
            }
        }

        stage('Deploy with Helm') {
            steps {
                dir('infra') {
                    sh """
                        helm upgrade --install contact-service \
                            charts/spring-service \
                            -f releases/contact-service.yaml \
                            --set image.tag=${IMAGE_TAG}
                    """
                }
            }
        }

        stage('Verify') {
            steps {
                sh """
                    kubectl rollout status deployment/contact-service -n dev --timeout=180s
                    echo ""
                    echo "===== PODS ====="
                    kubectl get pods -n dev -l app=contact-service
                    echo ""
                    echo "===== SERVICE ====="
                    kubectl get svc contact-service-service -n dev
                    echo ""
                    sleep 10
                    curl -s http://localhost:30080/actuator/health || echo "Starting..."
                """
            }
        }
    }

    post {
        success { echo '✅ Deploy exitoso! App en http://TU_IP_CONTABO:30080' }
        failure { echo '❌ Pipeline falló. Revisa los logs.' }
        always  { sh 'docker image prune -f || true' }
    }
}