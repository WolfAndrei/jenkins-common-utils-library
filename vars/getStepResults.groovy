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

//            for (def entry in getDownStreamJobAndBuildNumber(row.node)) {
//                nodeInfo.downstream["${entry.key}-${entry.value}"] = getStepResults(entry.key, entry.value)
//            }
            result << nodeInfo
        }
    }
//    log(result)
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