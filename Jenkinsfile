pipeline {
    agent any

    environment {
        registry = "goodspeed57/webjenkins"
        registryCredential = 'DockerHubAccount'
    }

    tools {
        maven 'maven'
        jdk 'JDK21'
    }

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Git Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/GooDSpeeD57/Squatrnbn.git'
            }
        }

        stage('Build Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage ('Build Docker Image') {
            steps {
                script {
                    docker.build('goodspeed57/webjenkins:latest','-f Dockerfile .')
                }
            }
        }

        stage ('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                        docker.image('goodspeed57/webjenkins:latest').push()
                    }
                }
            }
        }

        stage ('Deploy docker-compose') {
            steps {
                script {
                    bat 'docker-compose up -d --build --force-recreate --remove-orphans'
                }
            }
        }

    }

    post {
        always {
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
        }
    }

}
