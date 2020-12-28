pipeline {
  agent any
  stages {
    stage('Cloning Git') {
      steps {
        git 'https://github.com/KiryhaPikoff/tamada'
      }
    }
    stage('JKS copying') {
      steps {
        sh "sudo cp /home/pikov_kirya/tmd-keys.jks ./src/main/resources/jks/"
      }
    }
    stage('application-prod.properties copying') {
      steps {
        sh "sudo cp /home/pikov_kirya/application-prod.properties ./src/main/resources/"
      }
    }
    stage('Create shared folder /tmd-files') {
      steps {
        sh "sudo mkdir tmd-files"
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