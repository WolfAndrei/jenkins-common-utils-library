@Library('jenkins-common-utils-library')

import com.ncloudtech.jenkins.Logger

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
                Logger.catchErrors(currentBuild, env)
            }
        }
    }
}