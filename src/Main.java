package src;

import src.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        long elementsNumber = 5;

        testStrategy(new HashMapStorageStrategy(), elementsNumber);

        testStrategy(new FileStorageStrategy(), elementsNumber);

        testStrategy(new OurHashBiMapStorageStrategy(), elementsNumber);

        testStrategy(new OurHashMapStorageStrategy(), elementsNumber);
    }
    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        Helper.printMessage(strategy.getClass().getSimpleName() + ":");

        Set<String> origStrings = new HashSet<>();

        for (int i = 0; i < elementsNumber; ++i) {
            origStrings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        Date startTimestamp = new Date();
        Set<Long> keys = getIds(shortener, origStrings);
        Date endTimestamp = new Date();
        long time = endTimestamp.getTime() - startTimestamp.getTime();
        Helper.printMessage("Время получения идентификаторов для " + elementsNumber + " строк: " + time);

        startTimestamp = new Date();
        Set<String> strings = getStrings(shortener, keys);
        endTimestamp = new Date();
        time = endTimestamp.getTime() - startTimestamp.getTime();
        Helper.printMessage("Время получения строк для " + elementsNumber + " идентификаторов: " + time);

        if (origStrings.equals(strings))
            Helper.printMessage("Тест пройден.");
        else
            Helper.printMessage("Тест не пройден.");

        Helper.printMessage("");
    }
    public static Set<Long> getIds(Shortener shortener, Set<String> strings){
        Set<Long> keys = new HashSet<>();
        for (String string : strings){
            keys.add(shortener.getId(string));
        }
        return keys;
    }
    public static Set<String> getStrings(Shortener shortener, Set<Long> keys){
        Set<String> strings = new HashSet<>();
        for (Long key : keys){
            strings.add(shortener.getString(key));
        }
        return strings;
    }
}
