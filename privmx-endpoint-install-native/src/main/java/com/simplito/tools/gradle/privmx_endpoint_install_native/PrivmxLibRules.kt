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

val myAttribute: Attribute<String> = Attribute.of("com.simplito.target", String::class.java)

@CacheableRule
abstract class PrivmxRule :
    ComponentMetadataRule { //val os: String, val arch: String, val classifier: String)
    private val nativeTargets = listOf("desktop", "android")

    abstract val runtimeVariantName: String


    @get:Inject
    abstract val objects: ObjectFactory

    override fun execute(_context: ComponentMetadataContext) {
        _context.details.withVariant(runtimeVariantName) { metadata ->
            metadata.attributes { container ->
                container.attributes.attribute(myAttribute, "none")
            }
        }
        nativeTargets.forEach { targetDefinition ->
            _context.details.addVariant(
                "${targetDefinition}-runtime",
                runtimeVariantName
            ) { metadata ->
                metadata.attributes { container ->
                    container.attributes.attribute(myAttribute, targetDefinition)
                }
                metadata.withFiles { filesMetadata ->
                    filesMetadata.addFile("${_context.details.id.name}-${_context.details.id.version}-${targetDefinition}.jar")
                }
            }
        }
    }
}

@CacheableRule
abstract class PrivMXJavaRule: PrivmxRule() {
    override val runtimeVariantName: String = "runtimeElements"
}


@CacheableRule
abstract class PrivMXKotlinRule: PrivmxRule() {
    override val runtimeVariantName: String = "jvmRuntimeElements-published"
}