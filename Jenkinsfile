pipeline {
  agent any
  stages {
    stage('Cloning Git') {
      steps {
        git 'https://github.com/KiryhaPikoff/tamada'
        sh "chmod 777 ."
      }
    }
    stage('JKS copying') {
      steps {
        sh "cp /home/pikov_kirya/tmd-keys.jks src/main/java/resources/jks"
      }
    }
    stage('application-prod.properties copying') {
      steps {
        sh "cp /home/pikov_kirya/application-prod.properties src/main/java/resources"
      }
    }
    stage('Build application') {
      steps {
        sh "sudo docker-compose build"
      }
    }
    stage('Up application') {
      steps {
        sh "sudo docker-compose up -d"
      }
    }
  }
}