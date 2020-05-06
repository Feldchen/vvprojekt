package th.hesselfeld;

import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

public class FileEvent {
    String fileName;
    Symbol symbol;

    public FileEvent(String fileName, WatchEvent.Kind<?> symbol) {
        this.fileName = fileName;
        if(symbol == StandardWatchEventKinds.ENTRY_CREATE)
            this.symbol = Symbol.CREATE;
        else if(symbol == StandardWatchEventKinds.ENTRY_DELETE)
            this.symbol = Symbol.DELETE;
        else
            this.symbol=Symbol.MODIFY;
    }
}
