[Properties to YAML Converter](https://props2yaml-codearte2foss.rhcloud.com/conversion) 
===
[![Build Status](https://travis-ci.org/Codearte/props2yaml.svg)](https://travis-ci.org/Codearte/props2yaml) [![Coverage Status](https://coveralls.io/repos/Codearte/props2yaml/badge.svg?branch=master&service=github)](https://coveralls.io/github/Codearte/props2yaml?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.codearte.props2yaml/props2yaml/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.codearte.props2yaml/props2yaml)

Library usage:

    String yaml = Props2YAML.fromContent(content).convert();
    String yaml = Props2YAML.fromFile(file).convert();
    String yaml = Props2YAML.fromFile(path).convert();

or from command line:

    $ mvn io.codearte.props2yaml:props2yaml-maven-plugin:convert -Dproperties=application.properties

or

    $ wget http://central.maven.org/maven2/io/codearte/props2yaml/props2yaml/0.3/props2yaml-0.3-jar-with-dependencies.jar
    $ java -jar props2yaml-0.3-jar-with-dependencies.jar application.properties

There is also IntelliJ IDEA plugin called "[Properties to YAML Converter](https://plugins.jetbrains.com/plugin/8000)" which provides easy way of using this library

Online converter: https://props2yaml-codearte2foss.rhcloud.com/conversion
