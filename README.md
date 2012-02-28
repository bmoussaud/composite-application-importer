# Composite Application Importer #

This document describes the functionality provided by the Composite Application Importer

See the **Deployit Reference Manual** for background information on Deployit and deployment concepts.

# Overview #

The Composite Application Importer is a Deployit importer allowing to define and create composite package based on a text descriptor.

# Requirements #

* **Deployit requirements**
	* **Deployit**: version 3.7
	
# Use #

Create a file with .cad extension (composite application descriptor). It follows the Java property file syntax.
The file should contains at least an application, a version.
each package is defined using the following syntax:
package.X.name=ApplicationName
package.X.version=ApplicationVersion

where X is a number, starting by 1

Example: the file define the PetCompositeApp/3.4 application based on 2 deployment package PetClinic-ear/1.0 and PetClinic-ear/2.0

	application=PetCompositeApp
	version=3.4

	package.1.name=PetClinic-ear
	package.1.version=1.0

	package.2.name=PetClinic-ear
	package.2.version=2.0

Put the file in the importable package directory or import it using the CLI.
