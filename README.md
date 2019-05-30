# wsdl-downloader-plugin
Plugin to download wsdls and resources


```xml
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
                                    <path>${basedir}/src/main/resources/wsdls</path>
                                </wsdl>
                            </wsdls>                   		
                        </configuration> 
                    </execution>
                    
               </executions>
          </plugin>
```          