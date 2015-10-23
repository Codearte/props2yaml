Properties to YAML Converter 
===
[![Build Status](https://travis-ci.org/Codearte/props2yaml.svg)](https://travis-ci.org/Codearte/props2yaml) [![Coverage Status](https://coveralls.io/repos/Codearte/props2yaml/badge.svg?branch=master&service=github)](https://coveralls.io/github/Codearte/props2yaml?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.codearte.props2yaml/props2yaml/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.codearte.props2yaml/props2yaml)

Library usage:

    String yaml = new Props2YAML(string).convert();

or from command line:

    $ mvn io.codearte.props2yaml:props2yaml-maven-plugin:convert -Dproperties=application.properties

or

    $ wget http://central.maven.org/maven2/io/codearte/props2yaml/props2yaml/0.2/props2yaml-0.2-jar-with-dependencies.jar
    $ java -jar props2yaml-0.2-jar-with-dependencies.jar -Dproperties=application.properties
