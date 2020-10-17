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
        docker.build('tamada-service')
      }
    }
  }
}