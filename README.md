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

### Properties on a node:

- `depth` is how far from the root node this node lies
- `name` is the node name: all nodes are named and must be unique among
  children

### Start a new tree root node

Create a new tree root with:

- `ListMapTree.newRoot(name)`

This returns the new root node.

### Add child nodes to any node

Create a new child node and add it:

- `node.newChild(name)`

If a sibling node is already named `name`, throw an
`IllegalArgumentException`.
This returns the new child node (if not throwing).

### Remove child nodes from any node

Remove a child node directly or by name:

- `node.removeChild(child)`
- `node.removeChild(name)`

This returns `true` or `false` if there were a child node with `name`.

### Add propeties to a node

Properties with values are typed: Binary Data (arrays of bytes), Integer
(signed integer numbers up to 64 bits), Text (string).

Add or change a property of any node with:

- `node.setProperty(name, value)`

This returns the previous property value, or `null` if the property is new.

Values for properties may be of these types:

- `EmptyPropertyValue` (no constructor \[\*])
- `BinaryDataPropertyValue` (constructed with a `ByteArray` parameter)
- `IntegerPropertyValue` (constructed with a `Long` parameter)
- `TextPropertyValue` (constructed with a `String` parameter)

\[\*] `EmptyPropertyValue` is special: it has no true value, but holds a
dummy `Empty` object. Use this when the presence of the property key is all
that you need, and do not need an actual value.

For convience, you may also assign properties with direct values:

- `node.setProperty(name, Empty)`)
- `node.setProperty(name, binaryData)`
- `node.setProperty(name, number)`
- `node.setProperty(name, text)`

### Remove properties from a node

Remove a property from a node with:

- `node.removeProperty(name)`

### Work with trees

As convenience, since child nodes are sorted by name, you may access them with
indices:

- `node[index]` yields the n<sup>th</sup> child node

Similarly for properties:

- `node[name]` yields the value of the property, or `null` if missing or
  empty
- `node[name] = null` removes the property
- `node[name] = Empty` sets the property to the empty value
- `node[name] = data` sets the property to a binary data value
- `node[name] = number` sets the property to an integer value
- `node[name] = text` sets the property to a text value

An example of using chained index accesses:

```
val firstFoo = tree[0]["FOO"] // "FOO" property of first child node
```
