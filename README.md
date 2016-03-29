# About Skink
Skink is a SAX-based XML/HTML parser intended to be lightweight (low memory usage, minimal dependencies) and appropriate for use in Android.

#Getting Started
NOTE: Skink is still a work in progress, and work continues toward a v0.1 release.

Skink offers 2 interaction models (referred to as "Node" and "Data").  The first, Node, uses NodeListeners that receive and react to Node events (i.e. StartElement, Text, EndElement).  This is best used for simple use cases such as pulling a handful of pieces of data from a webpage or XML.  The second, Data, uses DataListeners that receive and react to Data events. Data events are raised by NodeListeners that have extracted individual pieces of data from the Node events and associated the data with a unique field identifier. This approach is designed for reading large amounts of data into a complex object structures.

# How to contribute pull requests
If you are interested in improving Skink, great! The Skink community looks forward to your contribution. Please follow this process:

1. Submit a bug or enhancement request as an issue.
2. [Fork](http://help.github.com/forking/) apexmob/skink. Ideally, create a new branch from your fork for your contribution to make it easier to merge your changes back.
3. Make your changes on the branch you hopefuly created in Step 2. Be sure that your code passes existing unit tests. Please add unit tests for your work if appropriate. It usually is.
4. Push your changes to your fork/branch in github. Don't push it to your master! If you do it will make it harder to submit new changes later.
5. Submit a pull request to apexmob from your commit page on github.