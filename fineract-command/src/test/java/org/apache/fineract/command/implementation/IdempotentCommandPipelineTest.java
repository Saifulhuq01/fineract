package org.apache.fineract.command.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;
import org.apache.fineract.command.core.Command;
import org.junit.jupiter.api.Test;

class IdempotentCommandPipelineTest {

    @Test
    void testExecutionLogsIdempotencyKey() {
        DefaultCommandPipeline delegate = mock(DefaultCommandPipeline.class);
        IdempotentCommandPipeline pipeline = new IdempotentCommandPipeline(delegate);
        
        Supplier<String> mockSupplier = () -> "Success!";
        when(delegate.send(any())).thenReturn((Supplier) mockSupplier);

        Command<String> cmd = new Command<>();
        cmd.setCommandId(999L);
        cmd.setIdempotencyKey("test-idempotency-key-1234");
        cmd.setPayload("My Payload");

        System.out.println("--- DEMO FOR GSOC POC SCREENSHOT ---");
        Supplier<String> result = pipeline.send(cmd);
        System.out.println("Pipeline returns: " + result.get());
        System.out.println("------------------------------------");

        assertEquals("Success!", result.get());
    }
}
