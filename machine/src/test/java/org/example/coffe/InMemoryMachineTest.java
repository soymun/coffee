package org.example.coffe;

import org.example.coffe.coffeeMachine.coffee.CoffeeMachine;
import org.example.coffe.coffeeMachine.coffee.impl.InMemoryCoffeeMachine;
import org.example.coffe.coffeeMachine.info.Info;
import org.example.coffe.coffeeMachine.info.impl.InMemoryInfoComponent;
import org.example.coffe.coffeeMachine.status.InMemoryStatusImpl;
import org.example.coffe.coffeeMachine.status.Status;
import org.example.coffe.coffeeMachine.status.StatusMachine;
import org.example.coffe.model.typeCoffe.Cappuccino;
import org.example.coffe.model.typeCoffe.CoffeeType;
import org.example.coffe.model.typeCoffe.Espresso;
import org.example.coffe.model.typeCoffe.Latte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

@ExtendWith(MockitoExtension.class)
class InMemoryMachineTest {

    StatusMachine statusMachine = new InMemoryStatusImpl();

    Info info = new InMemoryInfoComponent(10, 450, 450, 450);

    CoffeeMachine coffeeMachine;

    @BeforeEach
    public void each(){
        info = new InMemoryInfoComponent(10, 450, 450, 450);
        coffeeMachine = new InMemoryCoffeeMachine(
                Map.of(
                        CoffeeType.CAPPUCCINO, new Cappuccino(500, 200, 200, 200),
                        CoffeeType.ESPRESSO, new Espresso(200, 200, 200),
                        CoffeeType.LATTE, new Latte(300, 300, 200, 100)
                ),
                statusMachine,
                info
        );
        statusMachine.setStatus(Status.READY);
    }

    @Test
    public void testMake(){
        coffeeMachine.make(CoffeeType.CAPPUCCINO);
        assertThat(statusMachine.getStatus()).isEqualTo(Status.READY);
    }

    @Test
    public void testMakeLatte(){
        coffeeMachine.make(CoffeeType.LATTE);
        assertThat(statusMachine.getStatus()).isEqualTo(Status.READY);
    }

    @Test
    public void testMakeESPRESSO(){
        coffeeMachine.make(CoffeeType.ESPRESSO);
        assertThat(statusMachine.getStatus()).isEqualTo(Status.READY);
    }

    @Test
    public void testStatus() throws InterruptedException {
        CompletableFuture.runAsync(() -> coffeeMachine.make(CoffeeType.CAPPUCCINO));
        Thread.sleep(200);
        assertThat(statusMachine.getStatus()).isEqualTo(Status.MAKE);
    }


    @Test
    public void testTwoCommand() throws InterruptedException {
        CompletableFuture.runAsync(() -> coffeeMachine.make(CoffeeType.CAPPUCCINO));
        CompletableFuture.runAsync(() ->coffeeMachine.make(CoffeeType.LATTE));
        Thread.sleep(200);
        assertThat(statusMachine.getStatus()).isEqualTo(Status.MAKE);
        assertThat(info.getInfo().getCups()).isEqualTo(9);
    }

    @Test
    public void testError(){
        coffeeMachine.make(CoffeeType.CAPPUCCINO);
        coffeeMachine.make(CoffeeType.CAPPUCCINO);
        assertThatException().isThrownBy(()-> coffeeMachine.make(CoffeeType.CAPPUCCINO));
    }

}
