package src;

import src.strategy.StorageStrategy;

public class Shortener {
    private Long lastId = 0L;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy strategy) {
        this.storageStrategy = strategy;
    }

    synchronized Long getId(String string){
        if (storageStrategy.containsValue(string)){
            return storageStrategy.getKey(string);
        }else {
            lastId++;
            storageStrategy.put(lastId, string);
        }
        storageStrategy.containsValue(string);
        return null;
    }
    synchronized String getString(Long id){
        return storageStrategy.getValue(id);
    }
}
