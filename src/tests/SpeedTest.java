package src.tests;

import org.junit.Assert;
import org.junit.Test;

import src.Helper;
import src.Shortener;
import src.strategy.HashBiMapStorageStrategy;
import src.strategy.HashMapStorageStrategy;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids){
        Date startTimeStamp = new Date();
        for (String s : strings){
            ids.add(shortener.getId(s));
        }
        Date endTimeStamp = new Date();
        return endTimeStamp.getTime() - startTimeStamp.getTime();
    }
    public long getTimeToGetStrings(Shortener shortener,Set<Long> ids,Set<String> strings){
        Date startTimeStamp = new Date();
        for (Long id : ids){
            strings.add(shortener.getString(id));
        }
        Date endTimeStamp = new Date();
        return endTimeStamp.getTime() - startTimeStamp.getTime();
    }
    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();

        for (int i = 0; i < 10000; ++i) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> ids1 = new HashSet<>();
        Set<String> strings1 = new HashSet<>();
        long stringToIdTime1 = getTimeToGetIds(shortener1, origStrings, ids1);
        long idToStringTime1 = getTimeToGetStrings(shortener1, ids1, strings1);

        Set<Long> ids2 = new HashSet<>();
        Set<String> strings2 = new HashSet<>();
        long stringToIdTime2 = getTimeToGetIds(shortener2, origStrings, ids2);
        long idToStringTime2 = getTimeToGetStrings(shortener2, ids2, strings2);

        Assert.assertTrue(stringToIdTime1 > stringToIdTime2);
        Assert.assertEquals(idToStringTime1, idToStringTime2, 30);
    }
}
