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

    def "Should read mullti line values"() {
        given:
            String props = '''a=line1\
line2'''
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ 'a: line1line2'
    }

    def "Should read array values"() {
        given:
            String props = "a: x, y, z"
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ "a: x, y, z"
    }

}
