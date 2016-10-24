package jmp.nosql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import static org.mockito.Mockito.*;

/**
 * Created by Nona_Sarokina on 10/23/2016.
 */
@RunWith(MockitoJUnitRunner.class)

public class MongoCLIRunnerTest {
    protected static final String NAME = "name";
    protected static final String VALUE = "value";

    protected static final String CREATE_OPTION = "1";
    protected static final String FIND_OPTION = "2";
    protected static final String DELETE_OPTION = "3";
    protected static final String EXIT_OPTION = "4";


    @InjectMocks
    MongoCLIRunner runner;

    @Mock
    BufferedReader reader;

    @Mock
    MongoEditHandler handler;

    @Test
    public void testRun_createRecordHasBeenCalled() throws Exception {
        when(reader.readLine()).thenReturn(CREATE_OPTION).thenReturn(NAME).thenReturn(VALUE).thenReturn("").thenReturn(EXIT_OPTION);
        runner.run();
        verify(runner.getProcessor(), times(1)).createDocument(new HashMap<String, Object>() {{
            put(NAME, VALUE);
        }});
    }

    @Test
    public void testRun_findRecordHasBeenCalled() throws Exception {
        when(reader.readLine()).thenReturn(FIND_OPTION).thenReturn(NAME).thenReturn(VALUE).thenReturn(EXIT_OPTION);
        runner.run();
        verify(runner.getProcessor(), times(1)).find(NAME, VALUE);
    }

    @Test
    public void testRun_deleteHasBeenCalled() throws Exception {
        when(reader.readLine()).thenReturn(DELETE_OPTION).thenReturn("id").thenReturn(EXIT_OPTION);
        runner.run();
        verify(runner.getProcessor(), times(1)).delete("id");
    }

    @Test
    public void testRun_exitHasBeenCalled() throws Exception {
        when(reader.readLine()).thenReturn(EXIT_OPTION).thenReturn("something else that shouldn't be entered");
        runner.run();
        verify(reader, times(1)).readLine();
    }

    @Test
    public void testRun_userAbleToChooseCreateOptionAfterAnyExceptionWasThrown() throws Exception {
        when(reader.readLine()).thenThrow(Exception.class).thenReturn(CREATE_OPTION).thenReturn(NAME).thenReturn(VALUE)
                .thenReturn("").thenReturn(EXIT_OPTION);
        runner.run();
        verify(runner.getProcessor(), times(1)).createDocument(anyMap());
    }

    @Test
    public void testRun_userAbleToChooseFindOptionAfterAnyExceptionWasThrown() throws Exception {
        when(reader.readLine()).thenThrow(Exception.class).thenReturn(FIND_OPTION).thenReturn(EXIT_OPTION);
        runner.run();
        verify(runner.getProcessor(), times(1)).find(anyString(), anyString());
    }

    @Test
    public void testRun_userAbleToChooseDeleteOptionAfterAnyExceptionWasThrown() throws Exception {
        when(reader.readLine()).thenThrow(Exception.class).thenReturn(DELETE_OPTION).thenReturn(EXIT_OPTION);
        runner.run();
        verify(runner.getProcessor(), times(1)).delete(anyString());
    }

    @Test
    public void testRun_userAbleToChooseExitOptionAfterAnyExceptionWasThrown() throws Exception {
        when(reader.readLine()).thenThrow(Exception.class).thenReturn(EXIT_OPTION).thenReturn(EXIT_OPTION);
        runner.run();
        verify(reader, times(2)).readLine();
    }

}