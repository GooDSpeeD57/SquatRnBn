pipeline {
    agent any

    environment {
        registry = "goodspeed57/webjenkins"
        registryCredential = 'DockerHubAccount'
    }

    tools {
        maven 'maven'
        jdk 'JDK21'
        allure 'Allure'
    }

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Build & Push en parallèle') {
            parallel {

                stage('Web') {
                    stages {
                        stage('Git Checkout Web') {
                            steps {
                                dir('web') {
                                    git branch: 'main', url: 'https://github.com/GooDSpeeD57/SquatRnbn.git'
                                }
                            }
                        }
                        stage('Build Maven Web') {
                            steps {
                                dir('web') {
                                    bat 'mvn clean package'
                                }
                            }
                        }
                        stage('Build Docker Web') {
                            steps {
                                script {
                                    dir('web') {
                                        docker.build('goodspeed57/webjenkins:1.0', '-f Dockerfile .')
                                    }
                                }
                            }
                        }
                        stage('Push Docker Web') {
                            steps {
                                script {
                                    docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                                        docker.image('goodspeed57/webjenkins:1.0').push()
                                    }
                                }
                            }
                        }
                    }
                }

                stage('API') {
                    stages {
                        stage('Git Checkout API') {
                            steps {
                                dir('api') {
                                    git branch: 'main', url: 'https://github.com/GooDSpeeD57/SquatrbNb.git'
                                }
                            }
                        }
                        stage('Build Maven API') {
                            steps {
                                dir('api') {
                                    bat 'mvn clean package'
                                }
                            }
                        }
                        stage('Build Docker API') {
                            steps {
                                script {
                                    dir('api') {
                                        docker.build('goodspeed57/apijenkins:1.0', '-f Dockerfile .')
                                    }
                                }
                            }
                        }
                        stage('Push Docker API') {
                            steps {
                                script {
                                    docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                                        docker.image('goodspeed57/apijenkins:1.0').push()
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        stage('Deploy docker-compose') {
            steps {
                dir('web') {
                    bat 'docker-compose down -v'
                    bat 'docker-compose up -d --build --remove-orphans'
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
                results: [[path: 'web/target/allure-results'],
                         [path: 'api/target/allure-results']
                ]
            ])
        }
    }
}