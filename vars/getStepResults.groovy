import org.jenkinsci.plugins.workflow.support.actions.LogStorageAction
import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

List<Map> call() {
    return getStepResults()
}

List<Map> getStepResults() {
    def result = []

    FlowGraphTable t = new FlowGraphTable(currentBuild.rawBuild.execution)
    t.build()
    for (def row in t.rows) {
        if (row.node.error) {
            def nodeInfo = [
                'name': "${row.node.displayName}",
                'url': "${env.JENKINS_URL}${row.node.url}",
                'displayName': "${row.node.error.displayName}",
                'error': "${row.node.error.error}",
                'logText': ""
            ]

            def storageAction = row.node.getAction(LogStorageAction)
            def output = new ByteArrayOutputStream()

            if (storageAction) {
                storageAction.logText.writeLogTo(0, output)

                nodeInfo.logText = "${output.toString()}"
                nodeInfo.url += 'log/'
            }

            result << nodeInfo
        }
    }
    return result
}
