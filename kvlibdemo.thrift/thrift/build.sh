#!/usr/bin/env bash
rm -rf gen-java
mkdir -p gen-java
thrift -r --gen java -out gen-java  bean.thrift 
