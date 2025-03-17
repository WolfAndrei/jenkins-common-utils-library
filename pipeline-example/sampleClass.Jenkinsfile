/*
 * Copyright (c) New Cloud Technologies, Ltd., 2013-2025
 *
 * You can not use the contents of the file in any way without New Cloud Technologies, Ltd. written permission.
 * To obtain such a permit, you should contact New Cloud Technologies, Ltd. at https://myoffice.ru/contacts/
 *
 */

@Library('jenkins-common-utils-library') import static com.ncloudtech.jenkins.Logger.*

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
                catchErrors(this)
            }
        }
    }
}