pipeline {
    agent any
    stages {
        stage("Verify tooling") {
            steps {
                sh '''
                docker version
                docker info
                docker compose version
                curl --version
                jq --version
                '''
            }
        }
        stage("Prune Docker data") {
            steps {
                sh 'docker system prune -a --volumes -f'
            }
        }
        stage("Start container") {
            steps {
                sh 'docker-compose build'
                sh 'docker-compose up'
            }
        }
    }
}