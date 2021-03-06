# Introduction

__You've made a web service or somebody has given you a WSDL. So what are the input and output?__ Check the WSDL? No, that's not readable. Instead you can use __wsdl2html__ to __generate a readable HTML page from a WSDL url, such as__ 

![Alt html-table](/doc/image/falabella.png?raw=true)

__Sometimes as a developer you haven't got the WSDL ready.__ Instead you've just finished the jax-ws stubs:

![Alt service](/doc/image/stub-order-soap-service.png?raw=true)

But you still want a HTML spec right away. In this case you can still use __wsdl2html__ to __generate a readable HTML page from jax-ws stubs__

Now I'm working in the inverse tool: [HTML2WSDL](https://github.com/asaidel/html2wsdl)

# How to run

## Run as a command line tool

```shell

mvn package 
cd target 
unzip target/wsdl2html-some-version-jarset.zip -d /path/to/your/dir

# Go to the direction of extraction and you will see an executable file. Run it like, 

./wsdl2html.sh http://.../some?wsdl /path/to/your/html/directory  # or wsdl2html.bat for windows

```


## Run it inside your application

In your pom.xml, add the following: 

```xml

	<repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
		...
	</repositories>


	<dependencies>
		<dependency>
			<groupId>com.github.asaidel</groupId>
			<artifactId>wsdl2html</artifactId>
			<version>3.0.2</version>
		</dependency>
		...
	</dependencies>	

```

//if you call this method in your code, make sure the jdk version you used to run your code is 
// no lower than that of the jdk used  by your "wsimport" to run in shell

String html = org.jaxws.wsdl2html.service.Wsdl2Html.generateHtml(wsdlUrl); 

To generate html from stub classes, check [Wsdl2HtmlITCase](src/test/java/org/jaxws/integrationtest/Wsdl2HtmlITCase.java), [FalabellaTCase](src/test/java/org/jaxws/integrationtest/FalabellaTCase.java), 
[FalabellaTCase2](src/test/java/org/jaxws/integrationtest/FalabellaTCase2.java) 
fails but I don't know why yet


# History
* 3.0.2
	* Merged from chenjianjx 3.0.2
	* JDK required 6+ again due merge

* 2.3
	* added order column in HTML output
	* JDK required 6->7 due error in one case

* 2.2
	* Merged from chenjianjx 2.0.2

* 2.1  
	* Fixed required column in HTML output, It was always Y
	* Added falabella test case and template applies to following:
		* added cardinality column in HTML output		
	* Deleted empty cells in HTML output. Implies easier copy/paste to other formats (excel, etc)!	
	* Minor changes

* 2.0.1
	* Latest fork from chenjianjx
