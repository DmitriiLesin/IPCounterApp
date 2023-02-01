Utility for counting unique IPv4 addresses in a large file (one ip per line)

The following interfaces and their implementation are presented
- IPv4AddressCounter
  - HashSetIPv4AddressCounter - the simple solution based on HashSet. The source strings are stored in a container, which leads to a large memory consumption in the case of a large number of unique ip addresses in file.
  - BitSetIPv4AddressCounter - the optimized version based on BitSets. Two BitSets are used, with the size Integer.MAX_VALUE. One is used to store positive values of integer, and the other for negative ones. Since the data in BitSet is stored in arrays of long, the program uses ~512 MB of memory regardless of the number of unique ip addresses in the file
- IPv4AddressToIntMapper
  - SimpleIPv4AddressToIntMapper - the simple solution - initialization of a new Inet4Address object based on the passed string and gets its hashcode.
  - ByteBufferIPv4AddressToIntMapper - the implementation from sun.net.util textToNumericFormatV4 adapted to avoid unnecessary checks and memory allocation, using one reusable ByteBuffer.

Also present
- Class "App" allows to run the counting on an ip-addresses file. The path to the file is expected as the first argument. The result is output to the console with the execution time in milliseconds
- Tests of BitSetIPv4AddressCounter and ByteBufferIPv4AddressToIntMapper classes
- JMH microbenchmark for evaluating differences in the performance of proposed solutions

Results

Counting unique ip-addresses on ~120 GB file. https://ecwid-vgv-storage.s3.eu-central-1.amazonaws.com/ip_addresses.zip
```
Number of unique ip addresses 1000000000, counting took 548258 ms.
```

Mapper classes
```text
MapperBenchmark.simpleIPv4AddressToIntMapper              1.1.1.1  avgt    5  20,705 ± 0,640  ns/op
MapperBenchmark.byteBufferIPv4AddressToIntMapper          1.1.1.1  avgt    5  23,870 ± 0,585  ns/op
MapperBenchmark.simpleIPv4AddressToIntMapper      255.255.255.255  avgt    5  28,775 ± 0,150  ns/op
MapperBenchmark.byteBufferIPv4AddressToIntMapper  255.255.255.255  avgt    5  44,953 ± 0,608  ns/op
```
Counter classes
```text
Benchmark                                   (ipCount)  Mode  Cnt    Score     Error  Units
CounterBenchmark.hashSetIPv4AddressCounter       1000  avgt    5    0,013 ±   0,001  ms/op
CounterBenchmark.bitSetIPv4AddressCounter        1000  avgt    5   23,479 ±   0,259  ms/op
CounterBenchmark.hashSetIPv4AddressCounter    1000000  avgt    5  129,136 ± 104,415  ms/op
CounterBenchmark.bitSetIPv4AddressCounter     1000000  avgt    5   42,116 ±   0,209  ms/op
```
All results on AMD Ryzen 5 5600H, 16 GB RAM, SSD SAMSUNG MZVL2512HCJQ-00B00