plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.codemodder.base)
    implementation(libs.codemodder.plugin.semgrep)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.codemodder.testutils)
    testRuntimeOnly(libs.junit.platform.launcher)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("io.codemodder.sample.App")
}

tasks.test {
    useJUnitPlatform()
}
