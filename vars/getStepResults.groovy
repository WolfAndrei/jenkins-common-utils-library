import org.jenkinsci.plugins.workflow.actions.TagsAction
import org.jenkinsci.plugins.workflow.graph.FlowNode
import org.jenkinsci.plugins.workflow.support.actions.LogStorageAction
import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

import java.util.regex.Matcher
import java.util.regex.Pattern

List<Map> call() {
    return getStepResults()
}

List<Map> getStepResults() {
    def result = []

    FlowGraphTable t = new FlowGraphTable(currentBuild.rawBuild.execution)
    t.build()

    for (def row in t.rows) {
//        def stageStatus = getStatus(row.node)
//        echo row.node.actions.toString()
//        echo "${row.node.displayName} $stageStatus"

        if (row.node.error) {
            def nodeInfo = [
                'name': "${row.node.displayName}",
                'url': "${env.JENKINS_URL}${row.node.url}",
                'error': "${row.node.error.error}",
            ]

            result << nodeInfo
        }
    }
    return result
}

String getStatus(FlowNode node) {
    def storageAction = node.getAction(LogStorageAction)

    if (storageAction) {
        def output = new ByteArrayOutputStream()
        storageAction.logText.writeLogTo(0, output)


        def regex = Pattern.compile("completed: (\\w+)", Pattern.DOTALL);
        def result = regex.matcher(output.toString());

//        echo output.toString()

        if (result) {
            return result[0][1]
        } else {
            return 'UNKNOWN1'
        }
    } else {
        return 'UNKNOWN'
    }
}
