
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

package com.simplito.privmx_endpoint_install_native;

import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test;

class BasicUnitTest {
    @Test
    fun checkPlugin(){
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.simplito.privmx-endpoint-install-native")

        assertTrue(project.pluginManager
            .hasPlugin("com.simplito.privmx-endpoint-install-native"));

        assertNotNull(project.tasks.named("privmxEndpointInstallJni"));
    }
}
