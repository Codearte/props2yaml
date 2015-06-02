package io.codearte.props2yaml

import spock.lang.Specification

class ConverterSpec extends Specification {

    def "Should read boolean"() {
        given:
            String props = 'a=true'
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ 'a: true'
    }

}
