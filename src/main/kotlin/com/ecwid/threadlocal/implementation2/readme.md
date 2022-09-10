## Implementation 1

This implementation is a straight-forward one - it just keeps a week hashmap with threads as keys and **MyThreadLocal** map as value.  
Using weak hashmaps allows to GC dead threads and their corresponding values in the map.

**MyThreadLocal** class is just a facade to access the global map.

**Main** file has an example that you can launch. There, 3 threads are started with their corresponding thread locals.  
An attempt to access them from the main thread fails.
Then another thread is created that removes its thread local value.
