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

import org.gradle.api.Project

fun isAGP(project: Project): Boolean{
    return project.plugins.hasPlugin("com.android.application")
}