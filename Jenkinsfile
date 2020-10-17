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