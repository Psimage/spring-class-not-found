package me.yarosbug.scnf;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RibbonAppTest {

    @Test
    public void ribbonIsOk() throws IOException, InterruptedException {
        ProcessBuilder proc = new ProcessBuilder("java", "-jar", "target/spring-class-not-found-ribbon-0.0.1-SNAPSHOT.jar");
        proc.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        proc.redirectError(ProcessBuilder.Redirect.INHERIT);

        var exitCode = proc.start().waitFor();

        assertEquals(0, exitCode);
    }
}
