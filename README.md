<a href="LICENSE.md">
<img src="https://unlicense.org/pd-icon.png" alt="Public Domain" align="right"/>
</a>

# Kotlin Rational

[![build](https://github.com/binkley/kotlin-list-map-tree/workflows/build/badge.svg)](https://github.com/binkley/kotlin-list-map-tree/actions)
[![issues](https://img.shields.io/github/issues/binkley/kotlin-list-map-tree.svg)](https://github.com/binkley/kotlin-list-map-tree/issues/)
[![pull requests](https://img.shields.io/github/issues-pr/binkley/kotlin-list-map-tree.svg)](https://github.com/binkley/kotlin-list-map-tree/pulls)
[![vulnerabilities](https://snyk.io/test/github/binkley/kotlin-list-map-tree/badge.svg)](https://snyk.io/test/github/binkley/kotlin-list-map-tree)
[![license](https://img.shields.io/badge/license-Public%20Domain-blue.svg)](http://unlicense.org/)

Experiment with viewing a tree as a list and a map.

## Build and try

To build, use `./batect build`.
(This is as calling `./mvnw clean verify` with the same JDK.)

This project assumes JDK 17.
There are no run-time dependencies beyond the Kotlin standard library.

## API

(See [the tests](./src/test/kotlin/hm/binkley/labs) for examples.)

### Start a new tree root

Create a new tree root with `ListMapTree.newRoot("<root name>")`.
This returns the node for the new root.

### Add child nodes to the root or another node

Add nodes with `node.newNode("<node name>")`.
"\<node name>" must be unique (else the call throws an
`IllegalArgumentException`).
This returns the new node.

### Add propeties to a node

Properties are typed: Text (string), Int (integer numbers up to 64 bits), or
arrays of bytes.

Add or change properties of a node with
`node.setProperty("FOO", <property value>)`.
This returns the previous property value, or `null` if the property is new.

Values for properties may be of these types:

- `BinaryDataPropertyValue` (constructed with a `ByteArray` parameter)
- `EmptyPropertyValue` (no constructor \[\*])
- `IntPropertyValue` (constructed with a `Long` parameter)
- `TextPropertyValue` (constructed with a `String` parameter)

\[\*] `EmptyPropertyValue` is special: it has no true value, but holds a
dummy `Empty` object. Use this when the presence of the property key is all
that you need, and do not need an actual value.

