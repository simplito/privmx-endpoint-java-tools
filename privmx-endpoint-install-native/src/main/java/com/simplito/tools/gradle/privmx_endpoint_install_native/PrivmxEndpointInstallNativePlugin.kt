
//
// PrivMX Endpoint Install Native.
// Copyright © 2024 Simplito sp. z o.o.
//
// This file is part of the PrivMX Platform (https://privmx.dev).
// This software is Licensed under the MIT License.
//
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.simplito.tools.gradle.privmx_endpoint_install_native

import org.gradle.api.Plugin
import org.gradle.api.Project

private const val TASK_NAME= "privmxEndpointInstallJni"
abstract class PrivmxEndpointInstallNativePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val task = project.tasks.register(
            TASK_NAME,
            PrivmxEndpointInstallNativeTask::class.java
        ) {
            it.group = "privmx endpoint"
            it.description = "Download and unzip native privmx endpoint libraries"
            it.doLast { _ ->
                it.execute()
            }
        }
        try {
            project.tasks.named("prepareKotlinBuildScriptModel").orNull?.dependsOn(task)
            project.tasks.named("compileJava").orNull?.dependsOn(task)
        }catch (_: Exception){}
    }
}