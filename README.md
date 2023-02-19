<a href="LICENSE.md">
<img src="https://unlicense.org/pd-icon.png" alt="Public Domain" align="right"/>
</a>

# Kotlin List Map Tree

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

## Concepts

A `ListMapTree` is [a tree data
structure](https://en.wikipedia.org/wiki/Tree_(data_structure)) where:

- Each node may have multiple child nodes where nodes are [sorted by
  name](https://en.wikipedia.org/wiki/Tree_(graph_theory)#Ordered_tree)
- Each node has properties ([an associative
  array](https://en.wikipedia.org/wiki/Associative_array) of key-value pairs)
  where names (keys) are [sorted
  strings](https://en.wikipedia.org/wiki/Associative_array#Ordered_dictionary)

Properties are typed:

- Empty signifying presence but without a value
- Binary data
- Integer data
- Text data

A similar model is [the Windows
Registry](https://en.wikipedia.org/wiki/Windows_Registry).

## API

(See [the tests](./src/test/kotlin/hm/binkley/labs) for examples.)

### Start a new tree root

Create a new tree root with `ListMapTree.newRoot("<root name>")`.
This returns the node for the new root.

### Add child nodes to the root or another node

Add a new child node with `node.newNode("<node name>")`.
"\<node name>" must be unique (else the call throws an
`IllegalArgumentException`).
This returns the new node.

### Add propeties to a node

Properties with values are typed: Binary Data (arrays of bytes), Integer
(signed integer numbers up to 64 bits), Text (string).

Add or change properties of a node with
`node.setProperty("FOO", <property value>)`.
This returns the previous property value, or `null` if the property is new.

Values for properties may be of these types:

- `BinaryDataPropertyValue` (constructed with a `ByteArray` parameter)
- `EmptyPropertyValue` (no constructor \[\*])
- `IntegerPropertyValue` (constructed with a `Long` parameter)
- `TextPropertyValue` (constructed with a `String` parameter)

\[\*] `EmptyPropertyValue` is special: it has no true value, but holds a
dummy `Empty` object. Use this when the presence of the property key is all
that you need, and do not need an actual value.

For convience, you may also assign properties with direct values:

- `node.setProperty("FOO")` sets an empty property (as does
  `node.setProperty("FOO", null)`)
- `node.setProperty("FOO", binaryData)`
- `node.setProperty("FOO", number)`
- `node.setProperty("FOO", text)`

### Work with trees

As convenience, since child nodes are sorted by name you may access them with
indices:

- `node[index]` yields the n<sup>th</sup> child of "node"

Similarly for properties:

- `node[key]` yields the value of the "key" property, or `null` for either a
  missing key or an empty value

- `node[key] = null` sets the "key" property to an empty value
- `node[key] = data` sets the "key" property to a binary data value
- `node[key] = number` sets the "key" property to an integer value
- `node[key] = text` sets the "key" property to a text value
