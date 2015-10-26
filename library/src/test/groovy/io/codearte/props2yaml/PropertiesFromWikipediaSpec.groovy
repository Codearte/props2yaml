package io.codearte.props2yaml

import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class PropertiesFromWikipediaSpec extends Specification {

    def "Should read wikipedia sample properties"() {
        given:
            String props = new File('src/test/resources/sample.properties').text
        when:
            String yaml = Props2YAML.fromContent(props).convert();
        then:
            yaml =~ 'website: http://en.wikipedia.org/'
            yaml =~ 'language: English'
            yaml =~ 'message: Welcome to Wikipedia!'
            yaml =~ 'key with spaces: This is the value that could be looked up with the key "key with spaces".'
            yaml =~ 'tab: "\\\\t"'
            yaml !=~ 'exclamation'
    }

    def "Should read wikipedia sample properties using file"() {
        given:
            File props = new File('src/test/resources/sample.properties')
        when:
            String yaml = Props2YAML.fromFile(props).convert();
        then:
            yaml
    }

    def "Should read wikipedia sample properties using path"() {
        given:
            Path props = Paths.get('src/test/resources/sample.properties')
        when:
            String yaml = Props2YAML.fromFile(props).convert();
        then:
            yaml
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
