package io.codearte.props2yaml

import spock.lang.Specification

class ArrayConverterSpec extends Specification {

    def "Should convert very simple case"() {
        when:
            String yaml = Props2YAML.fromContent('foo[0]=true').convert();
        then:
            yaml =~ 'foo:'
            yaml =~ '- true'
    }

    def "Should convert to array when property is digit"() {
        when:
            String yaml = new Props2YAML('foo.0.bar=true').convert();
        then:
            yaml =~ 'foo:'
            yaml =~ '-   bar: true'
    }

    def "Should convert sample example with array"() {
        when:
            String yaml = new Props2YAML('foo[0].skip=true').convert();
        then:
            yaml =~ 'foo:'
            yaml =~ '-   skip: true'
    }

    def "Should convert example 2 with array"() {
        given:
            String props = 'foo.bar[0].a=true'
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ 'foo:'
            yaml =~ '    bar:'
            yaml =~ '    -   a: true'
    }

    def "Should convert example 3 with array"() {
        when:
            String yaml = new Props2YAML('''foo[0]=true
                                            foo[1]=false''').convert();
        then:
            yaml =~ 'foo:'
            yaml =~ '- true'
            yaml =~ '- false'
    }

    def "Should convert example 4 with array"() {
        given:
            String props = 'foo[0].bar=true\n' +
                    'foo[1].bar=false'
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ 'foo:'
            yaml =~ '-   bar: true'
            yaml =~ '-   bar: false'
    }

    def "Should convert example 5 with array"() {
        when:
            String yaml = new Props2YAML('''foo[0].first=true
                                            foo[0].second=false''').convert();
        then:
            yaml =~ 'foo:'
            yaml =~ '-   first: true'
            yaml =~ '    second: false'
    }

    def "Should convert example 2-dim array"() {
        when:
            String yaml = new Props2YAML('''foo[0].a[0].first=true
                                            foo[0].a[1].first=false
                                            foo[0].a[1].second=false''').convert();
        then:
            yaml =~ 'foo:'
            yaml =~ '-   a:'
            yaml =~ '    -   first: true'
            yaml =~ '    -   first: false'
            yaml =~ '        second: false'
    }

}
