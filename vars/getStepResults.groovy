import hudson.model.FreeStyleBuild
import hudson.model.Run
import io.jenkins.blueocean.listeners.NodeDownstreamBuildAction
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.support.actions.LogStorageAction
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

List<Map> call() {
    return getStepResults(currentBuild.rawBuild)
}

List<Map> getStepResults(Run<?,?> build) {
    def result = []

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
                Run<?, ?> run = Jenkins.instance.getItemByFullName(entry.key).getLastBuild()
                 nodeInfo.downstream["${entry.key}-${entry.value}"] = getStepResults(
                     run
                )
            }
            result << nodeInfo
        }
    }
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