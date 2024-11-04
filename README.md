# PrivMX Java Tools
This repository contains tools which simplify using PrivMX libraries in JVMs.

## privmx-endpoint-install-native 
This module implements a Gradle plugin that automates the process of downloading shared libraries from https://builds.simplito.com for Privmx Endpoint Java libraries.

### Options

#### - `version` (required)
The version of the native shared libraries to download.

#### - `channel` (optional)
Channel is part of the URL of the libraries. The default channel for libraries is `main` (https://builds.simplito.com/java/main).

#### - `platform` (optional)
A list of platforms (as a pair of operating system and architecture) for which to download the native shared library.
The default is the building platform.

### Usage
1. Add `mavenCentral()` repository to your `settings.gradle`:

```groovy
pluginManagement {
    repositories{
        mavenCentral()
    }
}
```

2. Add plugin in `build.gradle`:

```groovy
plugins {
    def pluginVersion = "1.0"
    id "com.simplito.privmx-endpoint-install-native" version "$pluginVersion"
}
```

3. Configure plugin in `build.gradle`:

```groovy
privmxEndpointInstallJni{
    version = $nativeLibVersion // Set the version of library to download, it should 
                                // match the privmx-endpoint-java dependency version

    // Set the channel of native libraries source
    channel = "main"

    // Set project supported platforms to download native libraries for them.
    platforms = [
            SupportedPlatforms.Darwin.arm64.platform,
            *SupportedPlatforms.Android.values().platform
    ]
}
```

## License information

PrivMX Endpoint Install Native.\
Copyright © 2024 Simplito sp. z o.o.

This file is part of the PrivMX Platform (https://privmx.dev). \
This software is Licensed under the MIT License.

PrivMX Endpoint and PrivMX Bridge are licensed under Licensed under the PrivMX Free License.\
See the License for the specific language governing permissions and limitations under the License.
