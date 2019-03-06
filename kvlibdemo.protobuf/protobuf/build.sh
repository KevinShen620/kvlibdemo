#!/usr/bin/env bash
mkdir -p out
protoc --java_out=out *.proto

