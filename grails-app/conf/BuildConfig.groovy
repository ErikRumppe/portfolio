grails.servlet.version = "2.5"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.work.dir = "work"

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
    
    	test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        build ":tomcat:$grailsVersion"

        compile ':cache:1.0.1'
   		compile ':cloud-support:1.0.8'
        compile ":fields:1.3"
   		compile ':heroku:1.0.1'
        compile ":platform-core:1.0.RC5"
   		compile ":twitter-bootstrap:3.0.0"
   		
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.3"
        runtime ":resources:1.2.RC2"
   		
        test(":spock:0.7") {
            exclude "spock-grails-support"
        }   		
    }
}
