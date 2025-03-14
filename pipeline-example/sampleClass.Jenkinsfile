@Library('jenkins-common-utils-library') _

// import com.example.JenkinsStageVisitor
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

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
//                 def failedStages = visitor.getFailedStages(currentBuild)
//                 echo "Failed stages:\n" + failedStages.join('\n')

                echo getStepResults().join('\n')

                // To get a list of just the stage names:
                //echo "Failed stage names: " + failedStages.displayName
            }
        }
    }
}

List<Map> getStepResults() {
    def result = []
    WorkflowRun build = currentBuild()
    FlowGraphTable t = new FlowGraphTable(build.execution)
    t.build()
    for (def row in t.rows) {
        if (row.node.error) {
            def nodeInfo = [
                    'name': "${row.node.displayName}",
                    'url': "${env.JENKINS_URL}${row.node.url}",
                    'error': "${row.node.error.error}",
                    'downstream': [:]

            ]
            if (row.node.getAction(LogStorageAction)) {
                nodeInfo.url += 'log/'
            }

            for (def entry in getDownStreamJobAndBuildNumber(row.node)) {
                nodeInfo.downstream["${entry.key}-${entry.value}"] = getStepResults(entry.key, entry.value)
            }
            result << nodeInfo
        }
    }
    log(result)
    return result
}

Map getDownStreamJobAndBuildNumber(def node) {
    Map downStreamJobsAndBuilds = [:]
    for (def action in node.getActions(NodeDownstreamBuildAction)) {
        def result = (action.link =~ /.*\/(?!\/)(.*)\/runs\/(.*)\//).findAll()
        if (result) {
            downStreamJobsAndBuilds[result[0][1]] = result[0][2]
        }
    }
    return downStreamJobsAndBuilds
}