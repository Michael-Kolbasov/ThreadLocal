## Implementation 1

This implementation tries to achieve the native approach without an ability to change the platform classes...  
Hence just emulating it.

**MyThread** class extends plain **Thread** and holds a personal **MyThreadLocal** map,  
so that concurrency is not an issue at all - each thread has its own map.

**MyThreadLocal** class is just a facade to access a thread's map.

**Main** file has an example that you can launch. There, 3 threads are started with their corresponding thread locals.  
An attempt to access them from the main thread fails.  
Then another thread is created that removes its thread local value.
