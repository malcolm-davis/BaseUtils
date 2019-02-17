BaseUtils
========

BaseUtils is a set of Java utilities classes.  The classes that do not fit into other third party libs have been brought into BaseUtils.

## Usage

**Copy** - Utility for making object copies without the need for adding cloning to an object.  Copy does a deep copy.<br/>
Note: There is no need for casting.

```
final Car firstCar = new Car("Ford");
final Car copyCar = Copy.copy(firstCar);
```
<br/>

<br/>
**StringDoubleUtil** - Converts Strings to doubles.
```
assertTrue( StringDoubleUtil.isDouble("1.0"));
assertFalse(StringDoubleUtil.isDouble("a"));
assertTrue( StringDoubleUtil.getDouble(null,0)==0);
assertTrue( StringDoubleUtil.getDouble("4.4",0)==4.4);
```

<br/>
**SimpleCache<I, T>** - An simple alternative to ehcache.

```
String cacheName = "simple";

// the minimum amount of time in milliseconds that the object should stay in cache
long expire = 1000; 

// period time in milliseconds between successive expire checks.
long period = 60000; 

m_store = new SimpleCache<Integer, Car>("Demo", expire, period);
....
final Integer key = Integer.valueOf(1);
m_store.put(key, new Car(key, "my car"));

final Integer key2 = Integer.valueOf(2);
m_store.put(key2, new Car(key2, "van"));

Car car = m_store.get(key);
System.out.println(car.m_name);

m_store.put(key, new Car(key, "my car update"));
car = m_store.get(key);
System.out.println(car.m_name);

try { Thread.sleep(10000); } 
catch (final InterruptedException excep) { }

m_store.put(key, new Car(key, "my car update again"));
car = m_store.get(key);
System.out.println(car.m_name);

// careful, due to timeout data may not be returned
car = m_store.get(key2);
if( car!=null) {
	System.out.println(car.m_name);
} else {
	System.out.println("No car corresponding to " + key2.toString());
}
```
<br/>


**CryptoService** Wraps to encrypt/decrypt functionality.

```
int iterationCount = 19;

String passPhrase = "YourPassPhrase";

// 8-byte Salt
byte[] salt ={ 
	(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56,
	(byte) 0x35, (byte) 0xE3, (byte) 0x03 
};

Crypto encrypter = CryptoService.newUtils(passPhrase, salt, iterationCount);
final String encrypted = encrypter.encrypt("Don't tell anybody!");
System.out.println("encrypted=" + encrypted);
System.out.println("decrypted="+encrypter.decrypt(encrypted));

Output:
encrypted=wvE4cZuDblPZpKs78bVTZHroAB7ouFGh
decrypted=Don't tell anybody!
```		

<br/>
**SyncWriter** - An interface and set of concrete classes that controls the writing from multiple threads.
```		
SyncCompressInMemory writer = new SyncFileWrite(new File("path to file"));
writer.write("some text");
...
writer.write("some text");
writer.close();
```		

<br/>
**HessianProxy** - Singleton wrapper to the HessianProxyFactory.

<br/>
**ServletUtil** - Dumps the entire HttpServletRequest structure, session state and cookies to a buffer for display, debugging or logging.
```
// request is a HttpServletRequest object
StringBuffer requestDump = ServletUtil.dumpRequest(request); 
```

<br/>
**WebClient** - A mechanism to detect client software, version and platform from a user-agent string.
```
// is windows?
WebClient.isWindows(request)

Platform platform =  WebClient.detect(request).getPlatform();

public enum Platform {
    MACOSX,
    WIN95,
    WIN98,
    WINNT,
    WIN2K,
    WINXP,
    WINVISTA,
    WIN7,
    WIN8,
    WIN81,
    LINUX,
    IOS,
    ANDROID,
    JAVA_ME,
    UNKNOWN
}
```


<br/>
**StressTest** - Java based stress test service.  Write the stress test in Java rather than using an elaborate GUI tools.
```
// Test: This is the test that will be executed.   
The action() contains the code to be executed
final  StressAction action = new StressAction() {
	@Override
	public String action(int threadId, int count) {
		//-----------------------------------------------
		// The count corresponds to the number of times this method has been called.
		// count is useful for things like logging or pull records from a list or file.

		//-----------------------------------------------
		// do something here.
		final String statusFlag = "complete";

		// The thread is to simulate activity
		try {
			Thread.sleep(10);
		} catch (InterruptedException excep) {
			excep.printStackTrace();
		}

		//-----------------------------------------------
		// return the status or message of the test.
		return statusFlag;
	}

	@Override
	public void cleanup() {
		// do some clean up here, such as closing database connections.
	}
};

// Determine how you want the class to be executed.
final String testName = "service call test";

final int numberOfThreads = 150;

final int numberOfActionsPerThread = 50;

final long delayBetweenActionsInMilliseconds = 50;

final boolean useRandomDelay = false;

final StressTest load = StressTest.newTest();
load.setDisplayDuringExecution(true);
load.run(testName, numberOfThreads, numberOfActionsPerThread,
	delayBetweenActionsInMilliseconds, useRandomDelay, action);

// display results
for (StressThead result : load.getStressThreads()) {
	System.out.println(result.toString());
	for( StressResults details : result.getResults() ) {
		System.out.println("\t" + details.toString());
	}
}
System.out.println("Total runtime = " + load.getTotalRuntime());
System.out.println("Average thread execution time= " + load.getAverageThreadTime());
```


**FieldTypes** - Reusable type information in the form of a enumeration with methods.

```
FieldTypes.DOUBLE
```
**CloseUtil** - Provides a central place for close behavior to reduce copy & pasting.

```
CloseUtil.close(fileHandle);
```
<br/>
