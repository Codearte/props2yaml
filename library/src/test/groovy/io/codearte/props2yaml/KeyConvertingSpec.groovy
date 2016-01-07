package io.codearte.props2yaml

import spock.lang.Specification

class KeyConvertingSpec extends Specification {

    def "Should read single line"() {
        given:
            String props = 'a.b.c = z'
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ 'a:'
            yaml =~ 'b:'
            yaml =~ 'c: z'
    }

    def "Should read tree"() {
        given:
            String props = '''
                              a.b.c = foo
                              a.b.d = bar
                           '''
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ 'a:'
            yaml =~ 'b:'
            yaml =~ 'c: foo'
            yaml =~ 'd: bar'
    }

    def "Should read duplicated properties"() {
        given:
            String props = '''a = 1
                              a = 2'''
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ 'a: 2'
            yaml != ~'a: 1'
    }

    def "Should read numeric subkeys"() {
        given:
            String props = '''some.1.key = somevalue
                              some.1.key2 = somevalue2'''
        when:
            String yaml = new Props2YAML(props).convert(false);
            System.out.println( yaml );
        then:
            yaml =~ 'some:'
            yaml =~ '\'1\':'
            yaml =~ 'key: somevalue'
            yaml =~ 'key2: somevalue2'
    }

}
