pipeline {
    agent any
    stages {
        stage('Run autotest') {
            steps {
               echo "Start run autotest"
               sh "mvn clean test allure:report"
               echo "Finish run autotest"
            }
        }
    }
    post {
        always {
            echo "I will always execute this!"
            allure([
                includeProperties: false,
                jdk: '',
                properties: [[key: 'allure.issues.tracker.pattern', value: 'http://tracker.company.com/%s']],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])
        }
    }
}