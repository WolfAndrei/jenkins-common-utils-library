package com.example


import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.support.visualization.table.FlowGraphTable

class JenkinsStageVisitor {
// Get information about all stages, including the failure causes.
//
// Returns a list of maps: [[id, displayName, result, errors]]
// The 'errors' member is a list of unique exceptions.
//    @NonCPS
//    List<Map> getStageResults(WorkflowRun run) {
//        def walker = new FlowGraphWalker(run.getExecution())
//        def scanner = new DepthFirstScanner()
//
//        Map errorsMap = [:]
//
//        scanner.setup(run.getExecution().getCurrentHeads());
//        for (FlowNode f : scan) {
//
//            def error = f.getAction(ErrorAction).error
//        }
//
//
//        // Get all pipeline nodes that represent stages
//        def visitor = new PipelineNodeGraphVisitor(build.rawBuild)
//        def stages = visitor.pipelineNodes.findAll { it.type == FlowNodeWrapper.NodeType.STAGE }
//
//        return stages.collect { stage ->
//
//            // Get all the errors from the stage
//            def errorActions = stage.getPipelineActions(ErrorAction)
//            def errors = errorActions?.collect{ it.error }.unique()
//
//            return [
//                id: stage.id,
//                displayName: stage.displayName,
//                result: "${stage.status.result}",
//                errors: errors
//            ]
//        }
//    }

// Get information of all failed stages
//    @NonCPS
//    List<Map> getFailedStages(RunWrapper build) {
//        return getStageResults(build).findAll { it.result == 'FAILURE' }
//    }

    WorkflowRun build;

    JenkinsStageVisitor(WorkflowRun build) {
     this.build = build
    }


    List<Map> getStepResults() {
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
}
