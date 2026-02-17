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

        stage('Git Checkout Web') {
            steps {
                script {
                    dir('web') {
                git branch: 'main', url: 'https://github.com/GooDSpeeD57/SquatRnbn.git'
                    }
                }
            }
        }

        stage('Build Maven Web') {
            steps {
                script {
                    dir('web') {
                        bat 'mvn clean package'
                    }
                }
            }
        }

        stage ('Build Docker Image Web') {
            steps {
                script {
                    dir('web') {
                        docker.build('goodspeed57/webjenkins:latest','-f Dockerfile .')
                    }
                }
            }
        }

        stage ('Push to Docker Hub Web') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                        docker.image('goodspeed57/webjenkins:latest').push()
                    }
                }
            }
        }

        stage('Git Checkout API') {
            steps {
                script {
                    dir('api') {
                        git branch: 'main', url: 'https://github.com/GooDSpeeD57/SquatrbNb.git'
                    }
                }
            }
        }

                stage('Build Maven API') {
                    steps {
                        script {
                            dir('api') {
                                bat 'mvn clean package'
                            }
                        }
                    }
                }

                stage ('Build Docker Image API') {
                    steps {
                        script {
                            dir('api') {
                                docker.build('goodspeed57/apijenkins:latest','-f Dockerfile .')
                            }
                        }
                    }
                }

                stage ('Push to Docker Hub API') {
                    steps {
                        script {
                            docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                                docker.image('goodspeed57/apijenkins:latest').push()
                            }
                        }
                    }
                }

        stage('Deploy docker-compose') {
            steps {
                script {
                    dir('web') {
                        bat 'docker-compose --env-file .env up -d --build --force-recreate --remove-orphans'
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
