plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
    implementation("io.codemodder:codemodder-base:0.69.2")
    implementation("io.codemodder:codemodder-plugin-semgrep:0.69.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("io.codemodder:codemodder-testutils:0.69.2")
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
