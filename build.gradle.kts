plugins {
	java
	id("org.springframework.boot") version "4.0.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "GitHub repositories viewer – recruitment task"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-restclient")

	testImplementation("org.springframework.boot:spring-boot-starter-test:4.0.0")
	testImplementation("org.springframework.boot:spring-boot-resttestclient")
	testImplementation("org.wiremock:wiremock-standalone:3.13.2")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
