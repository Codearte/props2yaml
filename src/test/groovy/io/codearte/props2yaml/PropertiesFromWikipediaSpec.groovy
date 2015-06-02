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
}
