package TTL_LRU;


/**
 * @author Crunchify.com
 */
 
public class TTL_LRU_TEST {
 
    public static void main(String[] args) throws InterruptedException {
 
    	TTL_LRU_TEST crunchifyCache = new TTL_LRU_TEST();
 
//        System.out.println("\n\n==========Test1: crunchifyTestAddRemoveObjects ==========");
//        crunchifyCache.crunchifyTestAddRemoveObjects();
        System.out.println("\n\n==========Test2: crunchifyTestExpiredCacheObjects ==========");
        crunchifyCache.crunchifyTestExpiredCacheObjects();
//        System.out.println("\n\n==========Test3: crunchifyTestObjectsCleanupTime ==========");
//        crunchifyCache.crunchifyTestObjectsCleanupTime();
    }
 
    private void crunchifyTestAddRemoveObjects() {
 
        // Test with timeToLiveInSeconds = 200 seconds
        // timerIntervalInSeconds = 500 seconds
        // maxItems = 6
        LRU <String, String> cache = new LRU<String, String>(200, 500, 6);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        cache.put("Google", "Google");
        cache.put("Microsoft", "Microsoft");
        cache.put("IBM", "IBM");
        cache.put("Facebook", "Facebook");
 
        System.out.println("6 Cache Object Added.. cache.size(): " + cache.size());
        cache.remove("IBM");
        System.out.println("One object removed.. cache.size(): " + cache.size());
 
        cache.put("Twitter", "Twitter");
        cache.put("SAP", "SAP");
        System.out.println("Two objects Added but reached maxItems.. cache.size(): " + cache.size());
 
    }
 
    private void crunchifyTestExpiredCacheObjects() throws InterruptedException {
 
        // Test with timeToLiveInSeconds = 1 second
        // timerIntervalInSeconds = 1 second
        // maxItems = 10
        LRU<String, String> cache = new LRU<String, String>(500,1,10);
 
        cache.put("eBay", "eBay");
        cache.put("Paypal", "Paypal");
        
        // Adding 3 seconds sleep.. Both above objects will be removed from
        // Cache because of timeToLiveInSeconds value
        Thread.sleep(3002);
 
        System.out.println("Two objects are added but reached timeToLive. cache.size(): " + cache.size());
 
    }
 
    private void crunchifyTestObjectsCleanupTime() throws InterruptedException {
        int size = 500000;
 
        // Test with timeToLiveInSeconds = 100 seconds
        // timerIntervalInSeconds = 100 seconds
        // maxItems = 500000
 
        LRU<String, String> cache = new LRU<String, String>(100,100, 500000);
 
        for (int i = 0; i < size; i++) {
            String value = Integer.toString(i);
            cache.put(value, value);
        }
 
        Thread.sleep(2000);
 
        long start = System.currentTimeMillis();
        cache.cleanup();
        double finish = (double) (System.currentTimeMillis() - start) / 1000.0;
 
        System.out.println("Cleanup times for " + size + " objects are " + finish + " s");
 
    }
}