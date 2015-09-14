The library is a different module in gradle and can be easily seperated into a library and can be referenced with. The Library is structured very simply.

RequestFactory - Simply a helper class to generate individual requests. Can be added more defaults and configuration options like connection Timeout etc

Executor - Manages threads, although not demonstrated this can easily be use to configure Thread Pool size which is necesseray to keep performance in small phones. It also caches and reuses threads rather than creating them again and again.

Request - Because it was asked to be able to cancel requests. After cancelling request dosent holds callbacks or anything that can get to the UI which may well have been destroyed. But after the completion of Request its responsed is cached.

Issues: The messy part in Request to be improved is handling of Cache, As I understand ImageRequest should have been separate from typical Json/XML/String body request. As has been done in Volley. But wanted to share that we can infact reuse code for both using dynamic untrusted type casting.

Cache - Although its an interface, the implemented class has its roots in android given LruCache. Sorry! it saved time. It should be implmented with Time of cached entities as well and responses should be invalited not only when cache size is overboard but also when response may be stale. Truly, caching http responses is done at HTTP Client layer, loads of Http clients do this like Netty and OkHttp.

Issues:
1. Simply the use of Object Cache is not typesafe but its very performant since it does not have to parse String responses or ByteStream.
2. Seprating ImageCache is very important and AFAIK the 80/20 rules in this case would suggest using a completely different implementation for ImageCache and ImageDowloading. The benefits of reusing code underweigh in this case as much is different between Image Downloading and Caching and Other webresponses. And code complexity would be reduced. Plus Images should use multilayered caches. This is apparent in many open source examples, seprate libraies like retrofit and picasso. Even Volley by Google has completely seprate hierachy to deal with both cases.

Response - Mostly unimplemented but can be implemented with status, respnose time, initiation time, cache hit/miss, Headers, redirects etc

I also believe in more Strongly Typed libraries for API Access which does strong query param checking, fixed header allowance, authentication and body.

The Image Quality is Low?

As I understand the api is using a thumbor service and I dont have the full api, so its just that. But I have still tried to scale it to fit, the best it can.


# Testing

The library has one demo test in this case. The test runner is Junit using Roboelectric.

I havent used any UI Testing frameworks in this app but I have been using spoon, monkey runner and espresso for past two years.

As you know there are following categories of testing in android

1. JUnit4 for tests with android dependencies(Like BitmapFactory). Can use mockito or run on android emulator or use roboelectric.
2. Junit4 tests without any android dependencies can be easily run.
3. Instrumentation or UITests can be controlled using espresso, and can run simultaneaosly on many devices using spoon runner.

I have experienced and used all above techniques. As opposed to what people call functional testing ( Blackbox and Whitebox), I am also familiar with Performance Testing and User Experience Testing.

Future:

1) Add Internet Connection utils so that Url Connection is faster when declining requests.
2) You will find some Todos to explain what I have left out.

All in all! I beleive there is a lot that could have been dont to impress by adding loads of open source widgets and animations to impressing the tech team with Compile Time Annotations, usage of Dagger, RxJava, Code Generation and test scripts. But if this does not meets your standards I would really love if you could spend an hour with me teaching where I went wrong.
