plugins {
    id("org.jetbrains.kotlin.plugin.spring") version "1.1.4"
}
apply {
    plugin("org.springframework.boot")
}
dependencies {
    val springBootVersion: String = parent.properties["springBootVersion"] as String
    compile(project(":backend:component"))
    compile("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    compile("org.springframework.boot:spring-boot-devtools:$springBootVersion")
}