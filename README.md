gradle-publishing-selector
======================

A small plugin which allows a project to specify which repository it should be published to.
Instead of defining a single repository manually (so that the publish task pushes to this repository):

```groovy
repositories {
    maven {
        name = 'myrepository1'
        url = 'http://nexus/myrepository1'
    }
    maven {
        name = 'myrepository2'
        url = 'http://nexus/myrepository2'
    }
    maven {
        name = 'myrepository2.snapshot'
        url = 'http://nexus/myrepository2'
    }
}

publishing {
    repositories {
        maven {
            name = 'myrepository'
            url = 'http://'
        }
    }
}
```

This plugin will allow you to specify the name of a repository, out of the repositories specified in the dependency repositories:

```groovy
apply plugin: 'publishing-selector'

repositories {
    maven {
        name = 'myrepository1'
        url = 'http://nexus/myrepository1'
    }
    maven {
        name = 'myrepository2'
        url = 'http://nexus/myrepository2'
    }
    maven {
        name = 'myrepository2.snapshot'
        url = 'http://nexus/myrepository2'
    }
}

publishingRepository {
    repository  'myrepository1'
}
```

This is especially useful in organisations which use an internal repository manager, with many repositories defined for different software groups. In this case it is preferred to define the repositories centrally, eg in init.gradle, while you would like the publish task to push to only a single repository, rather than all known ones.

Snapshot versions
======================
If your project versions ends with '-SNAPSHOT', it will be matched against the specified repository appended by the 'snapshotExtension' property of the extension. This defaults to '.snapshot'. Therefore if the example component was versioned 1.0-SNAPSHOT, it would publish to repository 'myrepository1.snapshot'. This can be changed like such:

```groovy
publishingRepository {
    snapshotExtension '-snapshot'
    repository  'myrepository1'
}
```

Installation
=====================
The plugin can be installed to maven local with 'gradle install'

It can be loaded with a buildscript including:

```groovy
buildscript {
    repositories {
        mavenLocal()
    }

    dependencies {
        classpath 'au.org.trogdor.gradle-plugins:publishing-selector:+'
    }
}
```
