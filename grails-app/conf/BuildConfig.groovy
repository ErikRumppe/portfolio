grails.servlet.version = "2.5"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.dependency.resolution = {
    inherits("global") {}
    log "error"
    checksums true
    legacyResolve false
    
    repositories {
        inherits true
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        // runtime 'mysql:mysql-connector-java:5.1.22'
    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.3"
        runtime ":resources:1.2"
        runtime ":database-migration:1.3.2"

        build ":tomcat:$grailsVersion"

        compile ':cache:1.0.1'
        compile ':heroku:1.0.1'
   		compile ':cloud-support:1.0.8'
    }
}
