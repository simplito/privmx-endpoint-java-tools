# PrivMX Java Tools
This repository contains tools which simplify using PrivMX libraries in JVMs.

## privmx-endpoint-install-native 
The `com.simplito.privmx-endpoint-install-native` Gradle plugin automates the process of downloading
and installing shared libraries for PrivMX Endpoint Java from [GitHub releases](https://github.com/simplito/privmx-endpoint-java/releases) assets
into the module directory with version specified in `privmxEndpointInstallJni` task configuration.
The downloaded libraries are installed in the `src/main/jniLibs` directory of your module.

## Options 

### `version` (required)
The version of the native shared libraries to download.

`public String version`


### `platforms` (optional)
A list of platforms (as a pair of operating system and architecture) for which to download the native shared library. 
The default is the building platform.

`public List<PrivmxEndpointPlatform> platforms`


## Usage

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
    def pluginVersion = "1.1"
    id "com.simplito.privmx-endpoint-install-native" version "$pluginVersion"
}
```

3. Configure plugin in `build.gradle`:

```groovy
privmxEndpointInstallJni{
    version = $nativeLibVersion // Set the version of library to download, it should 
                                // match the privmx-endpoint-java dependency version

    // Set project supported platforms to download native libraries for them.
    platforms = [
            SupportedPlatforms.Darwin.arm64.platform,
            *SupportedPlatforms.Android.values().platform
    ]
}
```

## License information

PrivMX Endpoint Install Native. \
Copyright © 2024 Simplito sp. z o.o.

This file is part of the PrivMX Platform (https://privmx.dev). \
This software is Licensed under the MIT License.

PrivMX Endpoint and PrivMX Bridge are licensed under the PrivMX Free License. \
See the License for the specific language governing permissions and limitations under the License.
