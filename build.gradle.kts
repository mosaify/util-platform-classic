import org.jetbrains.kotlin.gradle.ExperimentalJsTestDsl
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootEnvSpec

repositories { mavenCentral() }

plugins {
    signing

    alias(libs.plugins.kotlin.plugin.multiplatform)

    alias(libs.plugins.maven.publish)

    alias(libs.plugins.ksp)
    alias(libs.plugins.kotest)
}

kotlin {
    jvmToolchain(providers.gradleProperty("versionOfJava").get().toInt())

    explicitApi()

    withSourcesJar(publish = true)

    js {
        browser {
            @OptIn(ExperimentalJsTestDsl::class)
            test {
                chromium()
            }
        }
        nodejs {
            useEsModules()
        }
    }

    jvm {}
    linuxX64()
    macosArm64()
    mingwX64()

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("klogging") { withJvm(); withJs() }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.io)
            implementation(project.dependencies.platform(kotlin("bom")))
        }

        getByName("kloggingMain").dependencies {
            implementation(libs.klogging)
        }

        commonTest.dependencies {
            implementation(project.dependencies.platform(libs.kotest.bom))
            implementation(libs.kotest.framework.engine)
            implementation(libs.kotest.assertions.core)
        }

        jvmTest.dependencies {
            implementation(project.dependencies.platform(libs.junit.bom))
            implementation(project.dependencies.platform(libs.kotest.bom))
            implementation(libs.kotest.runner.junit6)
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

signing {
    useGpgCmd()
}

mavenPublishing {
    signAllPublications()

    publishToMavenCentral()

    pom {
        name = "util-platform-classic"
        description = "A basic platform utils library (Kotlin Multiplatform)"
        url = "https://github.com/mosaify/util-platform-classic"

        licenses {
            license {
                name = "The MIT License"
                url = "https://github.com/mosaify/util-platform-classic/blob/main/LICENSE"
            }
        }

        developers {
            developer {
                id = "samoylenko"
                name = "Michael Samoylenko"
                url = "https://github.com/samoylenko"
                roles.add("owner")
            }
        }

        contributors {
            contributor {
                name = "Vadim Kuhay"
                url = "https://github.com/rdd13r"
            }
        }

        scm {
            url = "https://github.com/mosaify/util-platform-classic"
            connection = "scm:git:git://github.com/mosaify/util-platform-classic.git"
            developerConnection = "scm:git:ssh://git@github.com:mosaify/util-platform-classic.git"
        }
    }
}

rootProject.plugins.withType(YarnPlugin::class.java) {
    rootProject.the<YarnRootEnvSpec>().apply {
        ignoreScripts.set(true)
        yarnLockAutoReplace.set(true)
        yarnLockMismatchReport.set(YarnLockMismatchReport.WARNING)
    }
}
