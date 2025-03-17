// @Library('jenkins-common-utils-library')
//
// import library('jenkins-common-utils-library')com.ncloudtech.jenkins.Logger

pipeline {
    agent any
    stages {
        stage('SuccessStage') {
            steps {
                echo 'Success'
            }
        }
        stage('FailedStage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                    readFile 'aaaaa'
                }
            }
        }
        stage('FailedStage2') {
            steps {
                echo 'Skipped because of error in FailedStage2'
                readFile 'dfgkjsdffj'
            }
        }
    }

    post {
        failure {
            script {
            library('jenkins-common-utils-library')com.ncloudtech.jenkins.Logger.catchErrors(currentBuild, env)
            }
        }
    }
}