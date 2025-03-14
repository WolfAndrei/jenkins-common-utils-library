import io.jenkins.blueocean.listeners.NodeDownstreamBuildAction
import org.jenkinsci.plugins.workflow.support.actions.LogStorageAction
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

List<Map> call() {
    return getStepResults()
}


List<Map> getStepResults() {
    def result = []

    RunWrapper build = currentBuild

    FlowGraphTable t = new FlowGraphTable(build.rawBuild.execution)
    t.build()
    for (def row in t.rows) {
        if (row.node.error) {
            def nodeInfo = [
                'name': "${row.node.displayName}",
                'url': "${env.JENKINS_URL}${row.node.url}",
                'error': "${row.node.error.error}",
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
    return result
}

Map getDownStreamJobAndBuildNumber(def node) {
    Map downStreamJobsAndBuilds = [:]
    NodeDownstreamBuildAction
    for (def action in node.getActions(NodeDownstreamBuildAction)) {
        def result = (action.link =~ /.*\/(?!\/)(.*)\/runs\/(.*)\//).findAll()
        if (result) {
            downStreamJobsAndBuilds[result[0][1]] = result[0][2]
        }
    }
    return downStreamJobsAndBuilds
}