package org.apache.fineract.command.implementation;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.command.core.Command;
import org.apache.fineract.command.core.CommandPipeline;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class IdempotentCommandPipeline implements CommandPipeline {

    private final DefaultCommandPipeline delegate;

    @Override
    public <REQ, RES> Supplier<RES> send(final Command<REQ> command) {
        System.out.println("INTERCEPTED COMMAND FOR IDEMPOTENCY POC: commandId=" + command.getCommandId() + ", idempotencyKey="
                + command.getIdempotencyKey());

        Supplier<RES> responseSupplier = delegate.send(command);

        return () -> {
            RES actualResponse = responseSupplier.get();
            return actualResponse;
        };
    }
}
