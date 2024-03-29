import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("plugin.jpa") version "1.3.31"
	id("org.springframework.boot") version "2.2.0.M4"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	id("com.bmuschko.docker-spring-boot-application") version "4.10.0"
	kotlin("jvm") version "1.3.31"
	kotlin("plugin.spring") version "1.3.31"
}

apply{
	plugin("io.spring.dependency-management")
}

group = "com.saivo"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springCloudVersion"] = "Hoxton.M1"

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-oauth2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("com.google.maps:google-maps-services:0.10.0")
	implementation("com.squareup.okhttp3:okhttp:4.2.0")
	implementation("com.google.code.gson:gson:2.8.5")
	implementation("com.twilio.sdk:twilio:7.42.0")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(group = "junit", module = "junit")
	}
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

docker {
	springBootApplication {
		baseImage.set("openjdk:8-alpine")
		tag.set("saivo/recommendo:$version")
		maintainer.set("Ian Mubangizi 'io@ianmubngizi.com'")
		ports.set(listOf(8080, 8080))
	}
}
