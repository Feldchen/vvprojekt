package th.hesselfeld;

import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.io.OutputStream;


public class WatchedDirectory {

    private HashMap<String, WatchedFile> list;

    public WatchedDirectory() {
    }

    public void update(FileEvent fileEvent) {
        WatchedFile file = list.get(fileEvent.fileName);
        file.transition(fileEvent.symbol);
    }

    public void sync(OutputStream out) {
        Gson gson = new Gson();

        try {
            for (Map.Entry<String, WatchedFile> entry : list.entrySet()) {
                String key = entry.getKey();
                WatchedFile value = entry.getValue();
                String valueAsJson = gson.toJson(value);
                out.write(valueAsJson.getBytes());
                out.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Map<String, WatchedFile> getList() {
        return list;
    }

    void setList(HashMap<String, WatchedFile> list) {
        this.list = list;
    }
}

