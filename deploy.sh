#!/usr/bin/env bash
mvn clean package deploy  -Dmaven.test.skip=true
