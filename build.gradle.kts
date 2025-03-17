plugins {
    groovy
}

sourceSets {
    main {
        groovy {
            setSrcDirs(setOf("src", "vars"))
        }
        resources {
            setSrcDirs(setOf("resources"))
        }
    }
//    test {
//        groovy {
//            srcDirs = ['test']
//        }
//    }
}

dependencies {
    implementation(libs.groovy)
    implementation(libs.jenkins.workflow.cps.jar)
    implementation(libs.jenkins.workflow.suport.jar)
}

val Provider<MinimalExternalModuleDependency>.jar: String
    get() = "${get().toString()}@jar"

//test {
//  dependsOn cleanTest
//
//  // don't stop if tests fail
//  ignoreFailures = true
//
//  // minimize logging
//  testLogging.maxGranularity = 0
//
//  // show stdout from tests
//  onOutput { dest, event -> print event.message }
//
//  // show test results
//  def results = []
//
//  afterTest { desc, result -> println "${desc.className.split("\\.")[-1]}: ${desc.name}: ${result.resultType}" }
//  afterSuite { desc, result ->
//    if (desc.className) {
//      results << result
//    }
//  }
//
//  // show summary
//  doLast {
//    println "Tests: ${results.sum { it.testCount }}, Failures: ${results.sum { it.failedTestCount }}" +
//        ", Errors: ${results.sum { it.exceptions.size() }}, Skipped: ${results.sum { it.skippedTestCount }}"
//  }
//
//}