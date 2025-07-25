# PrivMX Java Tools
This repository contains tools which simplify using PrivMX libraries in JVMs.

## privmx-endpoint-install-native 
The Gradle plugin automates the process of downloading native libraries artifacts and attaching
them to `runtimeClasspath`. This plugin supports `com.simplito.java:privmx-endpoint` and
`com.simplito.kotlin:privmx-endpoint` modules in **Java**, **Android** and **Kotlin** projects.

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
    def pluginVersion = "2.0"
    id "com.simplito.privmx-endpoint-install-native" version "$pluginVersion"
}
```

## License information

PrivMX Endpoint Install Native. \
Copyright © 2024 Simplito sp. z o.o.

This file is part of the PrivMX Platform (https://privmx.dev). \
This software is Licensed under the MIT License.

PrivMX Endpoint and PrivMX Bridge are licensed under the PrivMX Free License. \
See the License for the specific language governing permissions and limitations under the License.
