# SabreRESTJavaBridge
Another example Sabre Java Bridge for managing REST API request and responses coded by Cometari Dedicated Solutions http://www.cometari.com

It provides access to Sabre Rest API and the access is realized by http (REST) protocol.
Please set your credentials in application.properties file.

The package should be deployed on Tomcat server and following configuration should be added to sever.xml 
in order to accept content-encoding:

```xml
<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"
compression="on" compressionMinSize="2048" noCompressionUserAgents="gozilla, traviata"
compressableMimeType="application/json,application/xml,text/html,text/xml,text/plain"/>
```
**URL pattern:** host/api/url

Example:

1. Directly request to Sabre API https://api.sabre.com/v1/lists/supported/countries 
2. Equivalent request to the bridge http://\<YOUR_HOST\>/api/v1/lists/supported/countries

Supported requests (URL) you can find in Sabre API documentation available at https://developer.sabre.com/docs/read/Home
