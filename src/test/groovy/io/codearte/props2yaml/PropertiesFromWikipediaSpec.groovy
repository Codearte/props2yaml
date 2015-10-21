package io.codearte.props2yaml

import spock.lang.Specification

class PropertiesFromWikipediaSpec extends Specification {

    def "Should read wikipedia sample properties"() {
        given:
            String props = new File('src/test/resources/sample.properties').text
        when:
            String yaml = new Props2YAML(props).convert();
        then:
            yaml =~ 'website: http://en.wikipedia.org/'
            yaml =~ 'language: English'
            yaml =~ 'message: Welcome to Wikipedia!'
            yaml =~ 'key with spaces: This is the value that could be looked up with the key "key with spaces".'
            yaml =~ 'tab: "\\\\t"'
            yaml !=~ 'exclamation'
    }

    def "Should read maciek sample properties"() {
        given:
            String props = new File('src/test/resources/maciek.properties').text
        when:
            String yaml = new Props2YAML(props).convert();
        println yaml
        then:
            yaml =~ '    -   filteredReqFields\\[m\\]: acceptNews'
            yaml =~ '        filteredReqHeaders\\[j\\]: header-req-to-remove-1'
            yaml =~ '    config\\[z\\]:'
    }
}
