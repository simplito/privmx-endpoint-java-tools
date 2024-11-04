
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

import org.gradle.internal.os.OperatingSystem
import java.io.Serializable

data class PrivmxEndpointPlatform(
    val os: String,
    val architecture: String
) : Serializable


private enum class Os {
    Android, Darwin;

    infix fun arch(arch: String) = PrivmxEndpointPlatform(name, arch)
}

data object SupportedPlatforms {
    enum class Android(val platform: PrivmxEndpointPlatform) {
        x86(Os.Android arch "x86"),
        x86_64(Os.Android arch "x86_64"),
        armeabi_v7a(Os.Android arch "armeabi-v7a"),
        arm64_v8a(Os.Android arch "arm64-v8a"),
    }

    enum class Darwin(val platform: PrivmxEndpointPlatform) {
        arm64(Os.Darwin arch "arm64"),
    }

    fun currentPlatform(): PrivmxEndpointPlatform? {
        val operatingSystem: String = OperatingSystem.current().nativePrefix
        val architecture: String = System.getProperty("os.arch")

        return when(operatingSystem){
            Os.Darwin.name.lowercase()->{
                Os.Darwin arch architecture.replaceFirst("aarch","arm")
            }
            else -> null
        }
    }
}