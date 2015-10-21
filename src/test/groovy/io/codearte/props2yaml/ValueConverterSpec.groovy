package io.codearte.props2yaml

import spock.lang.Specification

class ValueConverterSpec extends Specification {

    def 'Should convert boolean'() {
        when:
            Object o = ValueConverter.asObject('true')
        then:
            o instanceof Boolean
            (o as Boolean).booleanValue()
    }

    def 'Should convert integer'() {
        when:
            Object o = ValueConverter.asObject('1')
        then:
            o instanceof Integer
            (o as Integer).intValue() == 1
    }

    def 'Should convert long'() {
        when:
            Object o = ValueConverter.asObject('1' * 15)
        then:
            o instanceof Long
            (o as Long).longValue() == 111111111111111
    }

    def 'Should convert string'() {
        when:
            Object o = ValueConverter.asObject('test')
        then:
            o instanceof String
            (o as String) == 'test'
    }
}
