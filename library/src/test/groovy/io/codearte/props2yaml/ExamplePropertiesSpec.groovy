package io.codearte.props2yaml

import spock.lang.Specification

class ExamplePropertiesSpec extends Specification {

    def "Should read log4j properties"() {
        given:
            String props = new File('src/test/resources/log4j.properties').text
        when:
            String yaml = Props2YAML.fromContent(props).convert();
            println yaml
        then:
            yaml == '''log4j:
    appender:
        stdout: org.apache.log4j.ConsoleAppender
        stdout.Target: System.out
        stdout.layout: org.apache.log4j.PatternLayout
        stdout.layout.ConversionPattern: '%d{ABSOLUTE} %5p %c{1}:%L - %m%n\'
    logger:
        org:
            hibernate: DEBUG
            hibernate.type: ALL
    rootLogger: INFO, stdout
'''
    }

    def "Should read maciek sample properties"() {
        given:
            String props = new File('src/test/resources/maciek.properties').text
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ '    -   filteredReqFields\\[m\\]: acceptNews'
            yaml =~ '        filteredReqHeaders\\[j\\]: header-req-to-remove-1'
            yaml =~ '    config\\[z\\]:'
    }
}
