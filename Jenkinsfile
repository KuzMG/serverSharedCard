pipeline {
    agent any
    environment {
      MAVEN_ARGS=" -e clean install"
  }
    stages {
        node('linux1') {
            stage('build') {
                steps {
                   withMaven(maven: 'MAVEN_ENV') {
                        sh 'mvn ${MAVEN_ARGS}'
            	    }
            	    stash 'name-of-the-stash'
                }
            }
        }
        node('linux1') {
            stage('docker-compose start') {
               steps {
                unstash 'name-of-the-stash'
            	sh 'docker-compose up --build'

                }
            }
    	}
    }
}
