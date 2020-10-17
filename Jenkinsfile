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
        sh "docker-compose build"
      }
    }
    stage('Up application') {
      steps {
        sh "docker-compose up -d"
      }
    }
  }
}