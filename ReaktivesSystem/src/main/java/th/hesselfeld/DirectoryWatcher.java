package th.hesselfeld;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.logging.*;


public class DirectoryWatcher {

    private Path directory;
    private WatchedDirectory watchedDirectory;


    public DirectoryWatcher(String directory, WatchedDirectory watchedDirectory) {
        this.directory = Path.of(directory);
        this.watchedDirectory = watchedDirectory;
    }

    public Path getDirectory() {
        return directory;
    }

    public static Logger LOGGER = Logger.getLogger(DirectoryWatcher.class.getName());

    public static void main(String[] args) {


        try {
            DirectoryWatcher.initializeLogger();
            CommandLine cmd = getCommandLine(args);
            WatchedDirectory watchedDirectory=null;
            DirectoryWatcher directoryWatcher = new DirectoryWatcher(cmd.getOptionValue("d"), watchedDirectory);
            directoryWatcher.observe(directoryWatcher.getDirectory().toString());

        } catch (IOException| ParseException| InterruptedException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            LOGGER.info("System terminated with error");
        }
        LOGGER.info("System terminted");
    }

    private static CommandLine getCommandLine(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(new Option("d", "directory", true, "watched directory"));
        CommandLineParser parser = new DefaultParser();
            return parser.parse(options, args);
    }

    private static void initializeLogger() throws IOException {
        LOGGER.info("Echo starting ...");
        LOGGER.info("Java Properties");
        LOGGER.info("Betriebssystem:" + System.getProperty("os.name"));
        LOGGER.info("Version des BS:" + System.getProperty("os.version"));
        LOGGER.info("Benutzer:" + System.getProperty("user.name"));
        LOGGER.info("Java Home:" + System.getProperty("java.home"));
        LOGGER.info("Java Version:" + System.getProperty("java.version"));
        LOGGER.info("Java Hersteller:" + System.getProperty("java.vendor"));
        LOGGER.info("Class Path:" + System.getProperty("java.class.path"));

        Handler fileHandler = new FileHandler("echo.log");
        fileHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fileHandler);
        LOGGER.setUseParentHandlers(false);
    }

    //Überwachungsfunktion
    public void observe(String directoryPath) throws IOException, InterruptedException {

            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path directory = Paths.get(directoryPath);
            directory.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        WatchKey watchKey;
            while ((watchKey= watchService.take()) != null) {
                watchKey.pollEvents().forEach(event -> {
                    String file = event.context().toString();
                    FileEvent fileEvent = new FileEvent(file, event.kind());
                    watchedDirectory.update(fileEvent);
                } );
            }
            watchKey.reset();
    }
}

