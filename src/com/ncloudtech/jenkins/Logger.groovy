/*
 * Copyright (c) New Cloud Technologies, Ltd., 2013-2025
 *
 * You can not use the contents of the file in any way without New Cloud Technologies, Ltd. written permission.
 * To obtain such a permit, you should contact New Cloud Technologies, Ltd. at https://myoffice.ru/contacts/
 *
 */

package com.ncloudtech.jenkins


import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

class Logger {
    static void catchErrors(script) {
        def currentBuild = script.currentBuild
        def env = script.env
        def errorsInfo = prepareErrorsInfo(currentBuild, env)

        if (currentBuild.description == null || currentBuild.description == "null") {
            currentBuild.description = errorsInfo
        } else {
            currentBuild.description += errorsInfo
        }
    }

    private static String prepareErrorsInfo(currentBuild, env) {
        return "Build errors:<br>" + collectErrors(currentBuild, env).collect {
            """<a href="${it.url}">${it.name}: ${it.error}</a>"""
        }.join("<br>")
    }

    private static List<Map> collectErrors(currentBuild, env) {
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
