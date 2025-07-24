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

import org.gradle.api.artifacts.CacheableRule
import org.gradle.api.artifacts.ComponentMetadataContext
import org.gradle.api.artifacts.ComponentMetadataRule
import org.gradle.api.attributes.Attribute
import javax.inject.Inject
import javax.naming.spi.ObjectFactory

val PrivMXNativeTargetAttribute: Attribute<String> =
    Attribute.of("com.simplito.target", String::class.java)

@CacheableRule
internal abstract class PrivmxRule : ComponentMetadataRule {
    private val nativeTargets = listOf("desktop", "android")

    abstract val runtimeVariantName: String

    @get:Inject
    abstract val objects: ObjectFactory

    override fun execute(_context: ComponentMetadataContext) {
        _context.details.withVariant(runtimeVariantName) { metadata ->
            metadata.attributes { container ->
                container.attributes.attribute(PrivMXNativeTargetAttribute, "none")
            }
        }
        nativeTargets.forEach { targetClassifier ->
            _context.details.addVariant(
                "${targetClassifier}-runtime",
                runtimeVariantName
            ) { metadata ->
                metadata.attributes { container ->
                    container.attributes.attribute(PrivMXNativeTargetAttribute, targetClassifier)
                }
                metadata.withFiles { filesMetadata ->
                    filesMetadata.addFile("${_context.details.id.name}-${_context.details.id.version}-${targetClassifier}.jar")
                }
            }
        }
    }
}

@CacheableRule
internal abstract class PrivMXJavaRule : PrivmxRule() {
    override val runtimeVariantName: String = "runtimeElements"
}


@CacheableRule
internal abstract class PrivMXKotlinRule : PrivmxRule() {
    override val runtimeVariantName: String = "jvmRuntimeElements-published"
}