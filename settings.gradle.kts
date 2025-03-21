/*
 * Copyright (c) New Cloud Technologies, Ltd., 2013-2025
 *
 * You can not use the contents of the file in any way without New Cloud Technologies, Ltd. written permission.
 * To obtain such a permit, you should contact New Cloud Technologies, Ltd. at https://myoffice.ru/contacts/
 *
 */

rootProject.name = "co-jenkins-shared-lib"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("./libs.versions.toml"))

            @Suppress("UnstableApiUsage")
            repositories {
                mavenCentral()
                maven {
                    url = uri("https://repo.jenkins-ci.org/releases/")
                }
            }
        }
    }
}
