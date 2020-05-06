package th.hesselfeld;

import org.junit.Test;

import java.nio.file.Path;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class WatchedFileTest {
    Date date = new Date();
    
    private WatchedFile watchedFile = new WatchedFile("ReaktiveSysteme", Path.of("VV"),date);
    
    @Test
    public void testStart() {
        assertThat(watchedFile.getCurrentState(), equalTo(State.CREATED));
    }

    @Test
    public void testCreatedtoCreatedWithCreate() {
        
        watchedFile.transition(Symbol.CREATE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.CREATED));
    }

    @Test
    public void testCreatedtoCreatedWithModify() {
        watchedFile.transition(Symbol.MODIFY);
        assertThat(watchedFile.getCurrentState(), equalTo(State.CREATED));
    }

    @Test
    public void testCreatedtoGone() {
        watchedFile.transition(Symbol.DELETE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.GONE));
    }

    @Test
    public void GoneWithDelete() {
        watchedFile.transition(Symbol.DELETE);
        watchedFile.transition(Symbol.DELETE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.GONE));
    }
    @Test
    public void GoneWithModify(){
        watchedFile.transition(Symbol.DELETE);
        watchedFile.transition(Symbol.MODIFY);
        assertThat(watchedFile.getCurrentState(), equalTo(State.GONE));
    }

    @Test
    public void GoneWithSync(){
        
        watchedFile.transition(Symbol.DELETE);
        watchedFile.transition(Symbol.SYNC);
        assertThat(watchedFile.getCurrentState(), equalTo(State.GONE));
    }

    @Test
    public void testCreatedToGoneToCreated() {
        
        watchedFile.transition(Symbol.DELETE);
        watchedFile.transition(Symbol.CREATE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.CREATED));

    }

    @Test
    public void testCreatedToInsync(){
        
        watchedFile.transition(Symbol.SYNC);
        assertThat(watchedFile.getCurrentState(), equalTo(State.INSYNC));
    }

    @Test
    public void testInsyncToInsync(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.SYNC);
        assertThat(watchedFile.getCurrentState(), equalTo(State.INSYNC));
    }

    @Test
    public void testInsyncToModifiedWithCreate(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.CREATE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.MODIFIED));
    }

    @Test
    public  void testInsyncToModifiedWithModify(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.MODIFY);
        assertThat(watchedFile.getCurrentState(), equalTo(State.MODIFIED));
    }

    @Test
    public  void testInsyncToDeleted(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.DELETE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.DELETED));
    }

    @Test
    public  void testDeletedToGone(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.DELETE);
        watchedFile.transition(Symbol.SYNC);
        assertThat(watchedFile.getCurrentState(), equalTo(State.GONE));
    }

    @Test
    public  void testGone(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.DELETE);
        watchedFile.transition(Symbol.DELETE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.DELETED));
    }

    @Test
    public void testDeletedtoModifiedWithCreate(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.DELETE);
        watchedFile.transition(Symbol.CREATE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.MODIFIED));
    }

    @Test
    public void testDeletedtoModifiedWithModify(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.DELETE);
        watchedFile.transition(Symbol.MODIFY);
        assertThat(watchedFile.getCurrentState(), equalTo(State.MODIFIED));
    }

    @Test
    public void testModifiedToDeleted(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.CREATE);
        watchedFile.transition(Symbol.DELETE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.DELETED));
    }

    @Test
    public void testModifiedWithCreate(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.CREATE);
        watchedFile.transition(Symbol.CREATE);
        assertThat(watchedFile.getCurrentState(), equalTo(State.MODIFIED));
    }

    @Test
    public void testModifiedWithModify(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.CREATE);
        watchedFile.transition(Symbol.MODIFY);
        assertThat(watchedFile.getCurrentState(), equalTo(State.MODIFIED));
    }

    @Test
    public void testModifiedWithSync(){
        
        watchedFile.transition(Symbol.SYNC);
        watchedFile.transition(Symbol.CREATE);
        watchedFile.transition(Symbol.SYNC);
        assertThat(watchedFile.getCurrentState(), equalTo(State.INSYNC));
    }



}

// created