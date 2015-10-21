package io.codearte.props2yaml

import spock.lang.Specification

class ArrayConverterSpec extends Specification {

    def "Should convert very simple case"() {
        given:
            String props = 'foo[0]=true'
        when:
            String yaml = new Props2YAML(props).convert();

            println "---"
            println yaml
        then:
            yaml == '''foo:
- true
'''
    }

    def "Should convert sample example with array"() {
        given:
            String props = 'foo[0].skip=true'
        when:
            String yaml = new Props2YAML(props).convert();
            println yaml
        then:
            yaml == 'foo:\n-   skip: true\n'
    }

    def "Should convert example 2 with array"() {
        given:
            String props = 'foo.bar[0].a=true'
        when:
            String yaml = new Props2YAML(props).convert();
            println yaml
        then:
            yaml == '''foo:
    bar:
    -   a: true
'''
    }

    def "Should convert example 3 with array"() {
        given:
            String props = 'foo[0]=true\n' +
                    'foo[1]=false'
        when:
            String yaml = new Props2YAML(props).convert();
            println yaml
        then:
            yaml == '''foo:
- true
- false
'''
    }

    def "Should convert example 4 with array"() {
        given:
            String props = 'foo[0].bar=true\n' +
                    'foo[1].bar=false'
        when:
            String yaml = new Props2YAML(props).convert();
            println '-----'
            println yaml
        then:
            yaml == '''foo:
-   bar: true
-   bar: false
'''
    }

    def "Should convert example 5 with array"() {
        given:
            String props = 'foo[0].first=true\n' +
                    'foo[0].second=false'
        when:
            String yaml = new Props2YAML(props).convert();
            println yaml
        then:
            yaml == '''foo:
-   first: true
    second: false
'''
    }

}
