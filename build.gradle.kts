plugins {
    // Apply the groovy Plugin to add support for Groovy.
    groovy
}

// follow the structure as dictated by Jenkins:
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

repositories {
//    jcenter()
    mavenCentral()
    maven {
        url = uri("https://repo.jenkins-ci.org/releases/")
    }
}

dependencies {
//    implementation("org.jenkins-ci.plugins.workflow:workflow-job:1505.vea_4b_20a_4a_495@jar")
//    implementation("org.codehaus.groovy:groovy-all:2.5.8")
//    implementation("org.apache.ivy:ivy:2.4.0")
    implementation("org.jenkins-ci.plugins.workflow:workflow-cps:4043.va_fb_de6a_a_8b_f5")
    implementation("org.jenkins-ci.main:jenkins-core:2.500")
    implementation("org.jenkins-ci.plugins.workflow:workflow-support:961.v51869f7b_d409@jar")
    implementation("org.jenkins-ci.plugins.workflow:workflow-api:1363.v03f731255494@jar")
    implementation("io.jenkins.blueocean:blueocean-pipeline-api-impl:1.27.17@jar")
//    implementation("io.jenkins.blueocean:blueocean-rest-impl:1.27.17@jar")
//    val staplerGAV = "org.kohsuke.stapler:stapler:1.255"
//    implementation(staplerGAV)
//    annotationProcessor(staplerGAV)
//    implementation("org.jenkins-ci.plugins.workflow:workflow-step-api:2.19@jar")
//    implementation("org.jenkins-ci.plugins:pipeline-utility-steps:2.2.0@jar")
//    testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
//    testImplementation("junit:junit:4.12")
//    testImplementation("com.lesfurets:jenkins-pipeline-unit:1.1")
}

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