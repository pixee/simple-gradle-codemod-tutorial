plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val codemodderVersion = "0.69.2"
    implementation("com.google.guava:guava:31.1-jre")
    implementation("io.codemodder:codemodder-base:$codemodderVersion")
    implementation("io.codemodder:codemodder-plugin-semgrep:$codemodderVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.codemodder:codemodder-testutils:$codemodderVersion")

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("io.codemodder.sample.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
