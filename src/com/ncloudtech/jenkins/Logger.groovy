package com.ncloudtech.jenkins

import hudson.model.Environment
import org.jenkinsci.plugins.workflow.cps.EnvActionImpl
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper
import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

class Logger {
    static void catchErrors(RunWrapper currentBuild, EnvActionImpl env) {
        def errorsInfo = prepareErrorsInfo(currentBuild, env)

        if (currentBuild.description == null || currentBuild.description == "null") {
            currentBuild.description = errorsInfo
        } else {
            currentBuild.description += errorsInfo
        }
    }

    private static String prepareErrorsInfo(RunWrapper currentBuild, EnvActionImpl env) {
        return "Build errors:<br>" + collectErrors(currentBuild, env).collect {
            """<a href="${it.url}">${it.name}: ${it.error}</a>"""
        }.join("<br>")
    }

    private static List<Map> collectErrors(RunWrapper currentBuild, EnvActionImpl env) {
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
