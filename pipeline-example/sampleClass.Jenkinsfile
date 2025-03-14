@Library('jenkins-common-utils-library') _

// import com.example.JenkinsStageVisitor

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
                readFile 'dfgkjsdffj'
            }
        }
        stage('SkippedStage') {
            steps {
                echo 'Skipped because of error in FailedStage'
            }
        }
    }

    post {
        failure {
            script {
                // Print information about all failed stages
//                 def visitor = new JenkinsStageVisitor()
                def failedStages = getStepResults()
                echo failedStages.join('\n')
//                 echo "Failed stages:\n" + failedStages.join('\n')

//                 echo getStepResults().join('\n')

                // To get a list of just the stage names:
                //echo "Failed stage names: " + failedStages.displayName
            }
        }
    }
}