package th.hesselfeld;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class WatchedDirectoryTest {
    //Setup
        Date date = new Date();
        HashMap<String, WatchedFile> testingList = new HashMap<String, WatchedFile>();

    @Test
    public void testUpdate() {
        WatchedFile watchedFile = new WatchedFile("h", Path.of("C://Test"), date);
        testingList.put("h",watchedFile);
        WatchedDirectory watchedDirectory = new WatchedDirectory();
        FileEvent newFileEvent = new FileEvent("h", StandardWatchEventKinds.ENTRY_DELETE);

       watchedDirectory.update(newFileEvent);
       assertThat(watchedFile.getCurrentState(), equalTo(State.GONE));
    }

    @Test
    public void sync() {
    }
}