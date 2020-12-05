pipeline {
  agent any
  stages {
    stage('Cloning Git') {
      steps {
        git 'https://github.com/KiryhaPikoff/tamada'
      }
    }
    stage('Build application') {
      steps {
        sh """
            sudo docker-compose build --build-arg \
                JKS=/home/pikov_kirya/tmd-keys.jks \
                PROD_SECURE_PROPS=/home/pikov_kirya/application-prod.properties
           """
      }
    }
    stage('Up application') {
      steps {
        sh "sudo docker-compose up -d"
      }
    }
  }
}