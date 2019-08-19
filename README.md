# wsdl-downloader-plugin
Plugin to download wsdls and resources


1. **Add and config plugin**


```xml

          ...
          <plugin>
                <groupId>com.ivoslabs</groupId>
                <artifactId>wsdl-downloader-maven-plugin</artifactId>
                <version>1.0.0</version>
                <executions>
                     
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>downloadWSDL</goal>
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
```


2. **Run Generate sources maven command**


```bash
mvn generate-sources
```


3. **Expected result**


```
       wsdls 
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

