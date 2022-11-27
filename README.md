##前言
> 编译时检查器，对用户是无感知的，基于APT实现不影响运行时性能，对不符合编码规范的进行强管控，
> 只要你有想象力可以动态扩展，目前实现了代码命名规范检查基础功能
> 后续考虑根据规则配置文件动态化构建检查逻辑，提高检查效率，灵活支持多业务复用，邮件/echarx消息通知实时提醒提供可视化功能

###1.检查时机：(1).编译构建build时 (2).CI提PR时，对于不符合命名规范要求的进行强行管控

###2.检查器适用场景：目前支持类/接口，变量/局部变量，方法/构造方法支持命名规范检查，同时支持java和kotlin项目

###3.检查器插件依赖支持：支持项目本地依赖和nexus远程依赖，方便多团队多业务复用

###4.检查器插件远程依赖生成
####4.1:修改compiler模块的gradle.properties中的版本VERSION_NAME 
####4.2:执行./gradlew :compiler:uploadArchives任务

###5.项目工程配置
####5.1 根目录中build.gradle配置插件Maven仓库下载地址
```groovy
buildscript {
    ext.kotlin_version = '1.4.10'
    ext.arouter_register_version = '1.0.2'

    repositories {
        mavenCentral()
        google()

        maven {//1.配置本地Maven仓库
            url this.file('repository')
        }
    }

    dependencies {
        classpath 'com.android.tools.build:gradle:4.1.1'
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "com.alibaba:arouter-register:$arouter_register_version"
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()

        //2.配置本地Maven仓库地址
        maven {
            url this.file('repository')
        }
    }
}
```

####5.2 java工程依赖检查器
######5.2.1 本地依赖
```groovy
annotationProcessor project(':compiler')
```
######5.2.2 远程依赖
```groovy
annotationProcessor 'com.ecarx:compiler:0.0.2'
```

####5.3 kotlin工程依赖检查器
######5.3.1 本地依赖
```groovy
apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-kapt'//1.依赖apt注解处理器插件
apply plugin: 'kotlin-android-extensions'

dependencies {
    kapt project(':compiler')//2.依赖检查器插件

    implementation "com.android.support:appcompat-v7:${SUPPORT_LIB_VERSION}"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
}
```

######5.3.2 远程依赖
```groovy
apply plugin: 'com.android.library'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-kapt'//1.依赖apt注解处理器插件
apply plugin: 'kotlin-android-extensions'

dependencies {
    kapt 'com.ecarx:compiler:0.0.2'//2.依赖检查器插件

    implementation "com.android.support:appcompat-v7:${SUPPORT_LIB_VERSION}"
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
}
```

###6.使用
```shell
 ./gradlew :app:build :javaModule:build :kotlinModule:build
```

参考文档:https://blog.csdn.net/u010126792/article/details/95614328