package th.hesselfeld;

import th.hesselfeld.State;
import th.hesselfeld.Symbol;

import java.nio.file.Path;
import java.util.Date;

public class WatchedFile {

    private String name;       //Filename
    private Path directory; //Directory
    private Date lastDate;      //date
    private State currentState;


//     INSYNC,           CREATED,               MODIFIED,            DELETED,    GONE

    State[][] transitionTable = {
            {State.MODIFIED, State.CREATED, State.MODIFIED, State.MODIFIED, State.CREATED},// CREATE
            {State.DELETED, State.GONE, State.DELETED, State.DELETED, State.GONE},// DELETE
            {State.INSYNC, State.INSYNC, State.INSYNC, State.GONE, State.GONE},// SYNC,
            {State.MODIFIED, State.CREATED, State.MODIFIED, State.MODIFIED, State.GONE}// MODIFY
    };
    // transition(Symbol.DELETE)
    public void transition(Symbol symbol){
        currentState = transitionTable[symbol.ordinal()][currentState.ordinal()];
    }

    public WatchedFile(String name, Path directory, Date lastDate ) {
        this.name = name;
        this.directory=directory;
        this.lastDate=lastDate;
        currentState = State.CREATED;
    }

    public State getCurrentState() {
        return currentState;
    }

    public String getName() {
        return name;
    }
    public Path getDirectory() {
        return directory;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public State[][] getTransitionTable() {
        return transitionTable;
    }
}

