import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

void catchErrors() {
    errorsInfo = prepareErrorsInfo()

    if (currentBuild.description == null || currentBuild.description == "null") {
        currentBuild.description = results
    } else {
        currentBuild.description += results
    }
}

private String prepareErrorsInfo() {
    return "Build errors:<br>" + collectErrors().collect {
        """<a href="${it.url}">${it.name}: ${it.error}</a>"""
    }.join("<br>")
}

private List<Map> collectErrors() {
    def result = []

    FlowGraphTable t = new FlowGraphTable(currentBuild.rawBuild.execution)
    t.build()

    for (def row in t.rows) {
        if (row.node.error) {
            def nodeInfo = [
                "name": "${row.node.displayName}",
                "url": "${env.JENKINS_URL}${row.node.url}",
                "error": "${row.node.error.error}"
            ]
            result << nodeInfo
        }
    }
    return result
}
