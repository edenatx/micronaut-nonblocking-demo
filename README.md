# Non Blocking Native Rest With Kotlin

This is an example project of running a non blocking RESTFul service in Kotlin. 
It is compiled natively and with start up times between 20 and 50 ms!!

The key to get this working is combining a few technologies. One is GraalVM's native-image 
compiler. The second is Micronaut. 

### Why use Micronaut and not Spring Boot?  
Reflection is not allowed in a natively compiled binary of Java using GraalVM. Micronaut
differentiates itself from Spring by compiling ahead of time and allowing for faster 
start ups. Micronaut for the most part operates exactly like Spring Boot. 


### Why use reactive framework libraries instead of Kotlin coroutines?
Micronaut currently does not support suspending functions in controllers yet. This will 
be coming in an upcoming release of Micronaut. At that point I will update this repo to 
use suspending functions. I think suspending functions will let the code look cleaner. I
also believe it'll end up being more efficient than the reactive library.

### How to Compile
You will need to have GraalVM installed as a pre-req. Look at the 
instructions for installing it below. 

Run the following gradle command \
`gradle buildNativeExecutable`

### How to see test the service
Run the following curl. \
`localhost:8080/product/PROD-001`

### How to the threads running?
Using the open source tool seige we can run the following command and expect to see 
the following. The idea is to have one thread always available for receiving requests 
and sending all potential requests to a thread pool.

`siege -c 20 -r 1 http://localhost:8080/product/PROD-003` 

```$xslt
siege -c 20 -r 1 http://localhost:8080/product/PROD-003
[alert] Zip encoding disabled; siege requires zlib support to enable it
** SIEGE 4.0.4
** Preparing 20 concurrent users for battle.
The server is now under siege...
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.64 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.65 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.65 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.65 secs:      68 bytes ==> GET  /product/PROD-003
HTTP/1.1 200     0.65 secs:      68 bytes ==> GET  /product/PROD-003

Transactions:		          20 hits
Availability:		      100.00 %
Elapsed time:		        0.65 secs
Data transferred:	        0.00 MB
Response time:		        0.64 secs
Transaction rate:	       30.77 trans/sec
Throughput:		        0.00 MB/sec
Concurrency:		       19.75
Successful transactions:          20
Failed transactions:	           0
Longest transaction:	        0.65
Shortest transaction:	        0.64
```

### Just how efficient is this?
Average start up time: \
26 ms

Memory and CPU profile after seige load\
tbd add in images of graphs

### What is a good use case for a native non-blocking REST service?
There are various use cases for this kind of project. I'm thinking of proxies. If you need
to write a service to do authentication and then proxy the request to a service behind 
with the token header you will be receiving a high volume of requests and waiting on calls
to the back services to finish before responding. 

### Credits
Loosely based on this blog post. \
https://e.printstacktrace.blog/micronaut-non-blocking-and-async-part1/
