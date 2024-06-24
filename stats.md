## Stats

| Agent | Test 1 | Test 2 | Test 3 | Test 4 | Test 5 | Average |
|----|----|----|----|----|----|----|
| Proxy | 9ms | 6ms | 8ms | 14 ms | 9ms | 9.2ms |
| Direct | 16ms | 7ms | 5ms | 5ms | 5ms | 7.6ms |

The table above shows the average time it takes to send a POST request to the server using the proxy server and a direct connection.


As we can see there is a very minimal difference between the two methods. The average time for the proxy is 9.2ms and the average time for the direct connection is 7.6ms. The direct connection is just faster by 1.6ms.

The test is done by sending a POST request to the server and measuring the time it takes to get a response. The test is done 5 times and the average time is calculated. The test is done on a local server and the client is on the same network as the server.

The POST request sent has a body,header and query parameters. The body is a JSON object, the header is a key-value pair and the query parameters are key-value pairs.
