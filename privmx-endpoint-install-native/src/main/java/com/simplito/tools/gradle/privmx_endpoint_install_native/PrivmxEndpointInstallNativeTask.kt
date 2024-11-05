
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

import de.undercouch.gradle.tasks.download.DownloadExtension
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.util.Properties
import kotlin.jvm.Throws

abstract class PrivmxEndpointInstallNativeTask : DefaultTask(){
    private val download = DownloadExtension(project)

    @Input
    var version: String? = null

    @Input
    var platforms = SupportedPlatforms.currentPlatform()?.let { listOf(it) } ?: listOf()

    @Input
    var channel = PrivmxEndpointChannel.MAIN.channel_name

    @OutputDirectory
    val nativeLibDirectory: Directory =
        project.layout.projectDirectory.dir("src/main/jniLibs")

    @OutputFile
    val versionFile: File =
        project.layout.projectDirectory.file("build/tmp/privmxEndpoint/jniInstall/jni-version.txt").asFile

    init {
        this.onlyIf {
            (!versionFile.exists() || versionFile.readText() != version) || platforms.any { !nativeLibDirectory.file("${it.os}/${it.architecture}").asFile.exists() }
        }
    }

    @Throws
    @TaskAction
    fun execute(){
        if(version == null) throw IllegalArgumentException("Version is not specified")
        val tmpDirectory: Directory = project.layout.projectDirectory.dir("build/tmp/privmxEndpoint/jniInstall/cache/libs/$version")
        Files.createDirectories(
            nativeLibDirectory.asFile.toPath()
        )
        Files.createDirectories(
            tmpDirectory.asFile.toPath()
        )
        if (!versionFile.exists()) {
            versionFile.createNewFile()
        }
        val isSingleOs = platforms.groupBy { it.os }.size == 1
        platforms.forEach { platform ->
            val formattedArchitecture = platform.architecture.replace("-","_")
            val packageName = "${platform.os.lowercase()}-$formattedArchitecture.zip"
            val zipFile = tmpDirectory.file(packageName).asFile
            download.run {
                it.src("https://github.com/simplito/privmx-endpoint-java/releases/download/$version/$packageName")
                it.dest(zipFile)
            }
            val unzipDir = nativeLibDirectory.dir(if (isSingleOs) platform.architecture else "${platform.os}/${platform.architecture}")
            val tree = project.zipTree(project.resources.gzip(zipFile.toPath()))
            if(tree.any { !unzipDir.file(it.name).asFile.exists() }) {
                project.copy {
                    it.from(tree)
                    it.into(unzipDir.asFile.toPath())
                }
            }
        }
        versionFile.writeText(version!!)
    }
}