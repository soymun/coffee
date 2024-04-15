package org.example.coffee.coffeeMachine.status;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class InMemoryStatusImpl implements StatusMachine, InitializingBean {

    private Status statusThreadLocal = null;

    @Override
    public Status getStatus() {
        return statusThreadLocal;
    }

    @Override
    public void setStatus(Status status) {
        this.statusThreadLocal= status;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        statusThreadLocal=Status.READY;
    }
}
