<a href="./LICENSE.md">
<img src="./images/public-domain.svg" alt="Public Domain"
align="right" width="20%" height="auto"/>
</a>

# Kotlin List Map Tree

[![build](https://github.com/binkley/kotlin-list-map-tree/workflows/build/badge.svg)](https://github.com/binkley/kotlin-list-map-tree/actions)
[![pull requests](https://img.shields.io/github/issues-pr/binkley/kotlin-list-map-tree.svg)](https://github.com/binkley/kotlin-list-map-tree/pulls)
[![issues](https://img.shields.io/github/issues/binkley/kotlin-list-map-tree.svg)](https://github.com/binkley/kotlin-list-map-tree/issues/)
[![vulnerabilities](https://snyk.io/test/github/binkley/kotlin-list-map-tree/badge.svg)](https://snyk.io/test/github/binkley/kotlin-list-map-tree)
[![license](https://img.shields.io/badge/license-Public%20Domain-blue.svg)](http://unlicense.org/)

Experiment with viewing a tree as a list and a map.

This is also a good project to copy as a Kotlin starter following [modern JVM
build practices](https://github.com/binkley/modern-java-practices).

## Build and try

To build, use `./mvnw clean verify`.
Try `./run` for a demonstration.

To build as CI would, use `./batect build`.
Try `./batect run` for a demonstration as CI would.

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

### Start a new tree root node

Create a new tree root (a node) with:

- `ListMapTree.newRoot(name)`

This returns the new root node, a parent node for other child nodes.

### Properties on a node

- `depth` is how far (how many node traversals) this node lies from the root
  node. The root node is 0 depth from itself
- `name` is the node name: all nodes are named, and must be unique among
  children of a parent, but may be non-unique for nodes with different
  parent nodes

### Add child nodes to any node

Create a new child node, and add it to a parent node:

- `parent.newChild(name)`

If a sibling node (a child node of the same parent) is already named `name`,
throw an `IllegalArgumentException`.
This returns the new child node when not throwing.

### Remove child nodes from any node

Remove a child node directly or by name from a parent node:

- `parent.removeChild(node)`
- `parent.removeChild(name)`

This returns `true` or `false` if there were such a child, and removing
succeeded.

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

Note that these patterns prioritize current node properties over access to
children by name (see issue #16).

An example of using chained index accesses:

```
root[0]["FOO"] // "FOO" property of first child node
child[1]["BAR"] // "BAR" property of second child node of another child node
```
