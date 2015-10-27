package io.codearte.props2yaml

import spock.lang.Specification

class ExamplePropertiesSpec extends Specification {

    def "Should read log4j properties"() {
        given:
            String props = new File('src/test/resources/log4j.properties').text
        when:
            String yaml = Props2YAML.fromContent(props).convert();
        then:
            yaml =~ '            hibernate.type: ALL'
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
