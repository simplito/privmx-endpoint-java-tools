//
// PrivMX Endpoint Install Native.
// Copyright © 2025 Simplito sp. z o.o.
//
// This file is part of the PrivMX Platform (https://privmx.dev).
// This software is Licensed under the MIT License.
//
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.simplito.tools.gradle.privmx_endpoint_install_native

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

abstract class PrivmxEndpointNativePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        //this is global for Java and Kotlin library
        project.dependencies.attributesSchema { handler ->
            handler.attribute(myAttribute)
        }

        //If Java some code
        project.dependencies.components { handler ->
            handler.withModule("com.simplito.java:privmx-endpoint", PrivMXJavaRule::class.java)
        }

        // If kotlin library then this code
        project.dependencies.components { handler ->
            handler.withModule("com.simplito.kotlin:privmx-endpoint-jvm", PrivMXKotlinRule::class.java)
        }

        setJVMPrivmxRule(project)
        if(isAGP(project)) {
            setAndroidPrivmxRule(project)
        }
    }

    private fun setJVMPrivmxRule(project: Project) {
        project.afterEvaluate {
            project.configurations.filter {
                it.name.lowercase().contains("runtimeclasspath")
            }.forEach {
                project.configurations.getAt(it.name).attributes { handler ->
                    handler.attribute(myAttribute, "desktop")
                }
            }
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    private fun setAndroidPrivmxRule(project: Project) {
        project.afterEvaluate {
            project.extensions.findByType(BaseAppModuleExtension::class.java)?.let { ext ->
                ext.applicationVariants.forEach { variant ->
                    project.configurations.getAt("${variant.name}RuntimeClasspath").attributes { handler ->
                        handler.attribute(myAttribute, "android")
                    }
                    project.configurations.getAt("${variant.name}UnitTestRuntimeClasspath").attributes { handler ->
                        handler.attribute(myAttribute, "android")
                    }
                }
            }
        }
        project.afterEvaluate {
            project.configurations.filter {
                it.name.lowercase().contains("runtimeclasspath") && it.name.lowercase().contains("android")
            }.forEach {
                project.configurations.getAt(it.name).attributes { handler ->
                    handler.attribute(myAttribute, "android")
                }
                println(it)
            }
        }
    }
}