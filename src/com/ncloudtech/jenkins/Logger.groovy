package com.ncloudtech.jenkins

import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

class Logger {
    static void catchErrors(RunWrapper currentBuild) {
        def errorsInfo = prepareErrorsInfo(currentBuild)

        if (currentBuild.description == null || currentBuild.description == "null") {
            currentBuild.description = errorsInfo
        } else {
            currentBuild.description += errorsInfo
        }
    }

    private static String prepareErrorsInfo(RunWrapper currentBuild) {
        return "Build errors:<br>" + collectErrors(currentBuild).collect {
            """<a href="${it.url}">${it.name}: ${it.error}</a>"""
        }.join("<br>")
    }

    private static List<Map> collectErrors(RunWrapper currentBuild) {
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

}
