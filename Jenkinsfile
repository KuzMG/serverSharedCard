pipeline {
    agent any
    environment {
      MAVEN_ARGS=" -e clean install"
  }
    stages {
        stage('build') {
            steps {
               withMaven(maven: 'MAVEN_ENV') {
                    sh 'export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64 | export PATH=$PATH:$JAVA_HOME/bin | mvn ${MAVEN_ARGS}'
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
