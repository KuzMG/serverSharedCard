pipeline {
    agent any
    environment {
      MAVEN_ARGS=" -e clean install"
  }
    stages {
        stage('Which Java?') {
            steps {
                sh 'java --version'
            }
        }
        stage('build') {
            steps {
               withMaven(maven: 'MAVEN_ENV') {
            		sh "mvn ${MAVEN_ARGS}"
        	}
            }
        }
        stage('docker-compose start') {
      	   steps {
       		sh 'docker-compose up -d'
      	    }
    	}
    }
}
