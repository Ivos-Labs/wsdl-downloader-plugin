# wsdl-downloader-plugin
Plugin to download wsdls and their XSDs



1 **Overview**

When we cosume a SOAP Web service, we have to do something like:


```xml
String endpoint = "https://host:port/someService?wsdl";
SomeServiceHttpPort httpPort = new SomeServiceHttpServices(endpoint).getSomeServiceHttpPort();
httpPort.someMethod(params);
```

The above code makes two requests to the endpoint. The first one is to ask for the WSDL file, and the second one
is to send the SOAP request. But, I have found some disvantages. First of all, the request to ask for the WSDL increases the latency
and it could be increased more according to the WSDL size, when you work with an external Web Service and the application have
 houndreds of concurrent requests, the latency matters. Furthermore, when the Web Service provider changes or adds a method, our
application will throw an exception, even whether the affected method is not being used

A common solution is, download the WSDL and save it as a resource

```
Project/src/main/resources/
  └────wsdls
       └───someService.wsdl
```

In this way we only have to use the local WSDL, and add the endoint using the BindingProvider

```xml
String wsdl = "wsdls/someService.wsdl";
String endpoint = "https://host:port/someService?wsdl";
SomeServiceHttpPort httpPort = new SomeServiceHttpServices(wsdl).getSomeServiceHttpPort();
((BindingProvider)httpPort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
httpPort.someMethod(params);
```

However, when working with multiple web services and/or when WSDL imports multiple XSDs, maintenance becomes difficult.
Because of this, I've created a plugin to download WSDLs and their XSDs


2. **Proyect Setup**

The plugin `wsdl-downloader-plugin` has the `downloadWSDLs` goal. This goal downloads multiples WSDLs and their XSDs, and refeactors the
 imports of the XSDs to use the local ones. This plugin allows us to save the files with a specific prefix and into a specific loation.

```xml
<project>
  ...
  <bluid>
    ...
    <plugins>
      ..
      <plugin>
        <groupId>com.ivoslabs</groupId>
        <artifactId>wsdl-downloader-plugin</artifactId>
        <version>1.0.0</version>
        <executions>
          <execution>
            <phase>generate-sources</phase>
            <goals>
              <goal>downloadWSDLs</goal>
            </goals>
            <configuration>
              <wsdls>

                <wsdl>
                  <url>http://server/serviceY?wsdl</url>
                  <prefix>serviceY</prefix>
                  <path>src/main/resources/wsdls</path>
                </wsdl>

                <wsdl>
                  <url>http://server/serviceX?wsdl</url>
                  <prefix>serviceX</prefix>
                  <path>src/main/resources/wsdls</path>
                </wsdl>

              </wsdls>
            </configuration>
          </execution>
         </executions>
      </plugin>
      ...
    <plugins>
    ...
  <bluid>
  ...
<project>
```



2.1 **Proxy Setup**

Additionally, we can configure a proxy using the `httpProxy` and `httpsProxy` tags.

 ```xml
 ...
<configuration>
    <httpProxy>domain/user:password@host:port</httpProxy>
    <httpsProxy>domain/user:password@host:port</httpsProxy>
    <wsdls>
    ....
    </wsdls>
</configuration>
```


3 **Download WSDL**

The plugin is executed in the `generate-sources` maven phase

```bash
mvn generate-sources
```

3.1 **Tip**

I suggest adding the plugin in a profile

```xml
<project>
  ...
  <profiles>
    <profile>
      <id>update</id>
      <build>
        <plugins>
          <plugin>
            <groupId>com.ivoslabs</groupId>
            <artifactId>wsdl-downloader-plugin</artifactId>
            <version>1.0.0</version>
            ...
          </plugin>
          ...
        </plugins>
      </build>
    </profile>
    ...
  </profiles>
  ...
<project>
```

In this way, the plugin only downloads the files when we use that profile instead of each compilation


```bash
mvn install -P update
```

4 **Expected result**


```
Project/src/main/resources/
  └────wsdls
       ├───serviceY.wsdl
       ├───serviceY_xsd_1.xsd
       ├───serviceY_xsd_2.xsd
       .....
       ├───serviceY_xsd_n.xsd
       ├───serviceX.wsdl
       ├───serviceX_xsd_1.xsd
       ├───serviceX_xsd_2.xsd
       .....
       └───serviceX_xsd_n.xsd

```

5 **Conclusion**

Now we are able to implement the `wsdl-downloader-plugin` in our projects, not only to improve latency times but also to facilitate the maintenance of our web services clients.


