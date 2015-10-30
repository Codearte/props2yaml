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

    def "Should read boolean in custom format"() {
        given:
            String props = 'a:true'
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
            String props = "a= x, y, z"
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ "a: x, y, z"
    }

    def "Should ignore whitespaces"() {
        given:
            String props = 'a    =     b'
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ "a: b"
    }

    def "Should convert elements with two levels of nesting"() {
        given:
            String props = 'a = x\n' +
                    'a.b = y'
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ "a: x"
            yaml =~ "a.b: y"
    }

    def "Should convert elements with three levels of nesting"() {
        given:
            String props = 'a = x\n' +
                    'a.b = y\n' +
                    'a.b.c = z'
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ "a: x"
            yaml =~ "a.b: y"
            yaml =~ "a.b.c: z"
    }

    def "Should convert elements with three levels of nesting and primitive values different than String"() {
        given:
            String props = 'a = 1\n' +
                    'a.b = true\n' +
                    'a.b.c = false'
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ "a: 1"
            yaml =~ "a.b: true"
            yaml =~ "a.b.c: false"
    }
}
