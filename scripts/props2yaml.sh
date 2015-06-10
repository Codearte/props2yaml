#!/usr/bin/env bash

mvn -f ../pom.xml clean compile assembly:single
java -jar ../target/*.jar $@
