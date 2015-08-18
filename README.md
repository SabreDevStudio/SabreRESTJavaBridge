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

You can also rename built package WAR to ROOT.WAR and deploy to <CATALINA_PATH>/webapps.

Example:

1. Directly request to Sabre API https://api.sabre.com/v1/lists/supported/countries 
2. Equivalent request to the bridge http://\<YOUR_HOST\>/api/v1/lists/supported/countries

Supported requests (URL) you can find in Sabre API documentation available at https://developer.sabre.com/docs/read/Home

## Disclaimer of Warranty and Limitation of Liability

This software and any compiled programs created using this software are furnished “as is” without warranty of any kind, including but not limited to the implied warranties of merchantability and fitness for a particular purpose. No oral or written information or advice given by Sabre, its agents or employees shall create a warranty or in any way increase the scope of this warranty, and you may not rely on any such information or advice.
Sabre does not warrant, guarantee, or make any representations regarding the use, or the results of the use, of this software, compiled programs created using this software, or written materials in terms of correctness, accuracy, reliability, currentness, or otherwise. The entire risk as to the results and performance of this software and any compiled applications created using this software is assumed by you. Neither Sabre nor anyone else who has been involved in the creation, production or delivery of this software shall be liable for any direct, indirect, consequential, or incidental damages (including damages for loss of business profits, business interruption, loss of business information, and the like) arising out of the use of or inability to use such product even if Sabre has been advised of the possibility of such damages.
