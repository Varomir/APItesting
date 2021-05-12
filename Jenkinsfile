pipeline {
    agent any
    stages {
        stage('Run autotest') {
            steps {
               echo "Start run autotest"
               sh "mvn clean test"
               echo "Finish run autotest"
            }
        }
    }
}