package com.example.reactive.programming;

import com.example.reactive.programming.service.FluxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

@SpringBootTest
public class FluxTest {
@Autowired
    private FluxService fluxService;

@Test
    public void FluxTest(){
    fluxService.getFLuxNames().subscribe(data->{
        System.out.println(data);
    });
}

    @Test
public void mapTest(){
    fluxService.mapExampleFlux().subscribe(data->{
        System.out.println(data);
    });

    Flux<String>capFlux=fluxService.mapExampleFlux();
    StepVerifier.create(capFlux)
            .expectNextCount(4)
            .verifyComplete();

        StepVerifier.create(capFlux)
                .expectNext("Ravi".toLowerCase(),"Shubham".toUpperCase(),"NIkhil".toUpperCase(),"Sanju".toUpperCase())
                .verifyComplete();
}


@Test
public void flatMapTEst() throws InterruptedException {

    fluxService.flatMapExampleFlux().subscribe(System.out::println);
    Thread.sleep(6000);
}

@Test
public void filterExampleTest(){
    Flux<String>filterFlux=fluxService.filterExampleFlux();
    StepVerifier.create(filterFlux)
            .expectNextCount(3)
            .verifyComplete();
}

@Test
public void transformExample(){
    Flux flux=fluxService.transformExample();
    StepVerifier.create(flux)
            .expectNextCount(4)
            .verifyComplete();
}

@Test
    public void ifExample(){
    Flux<String>flux=fluxService.ifExample(5);
    StepVerifier.create(flux)
            .expectNextCount(2)
            .verifyComplete();
}

    @Test
    public void ifExample2(){
        Flux<String>flux=fluxService.ifExample(10);
        StepVerifier.create(flux)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void switchExample(){
        Flux<String>flux=fluxService.switchExample(10);
        StepVerifier.create(flux)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void concatExample(){
    Flux flux=fluxService.concatExample().log();
    StepVerifier.create(flux)
            .expectNextCount(7)
            .verifyComplete();
    }

    @Test
    public void mergeWithExample(){
    Flux<String>flux=fluxService.mergeWithExample().log();
    StepVerifier.create(flux)
            .expectNextCount(7)
            .verifyComplete();
    }

    @Test
    public void zipWithExample(){
    Flux<Tuple2<String,Integer>>tuple2Flux=fluxService.zipExample().log();
    StepVerifier.create(tuple2Flux)
            .expectNextCount(4)
            .verifyComplete();
    }

    @Test
    public void  zipExample2(){
    Flux<String>flux=fluxService.zipExample2();
    StepVerifier.create(flux)
            .expectNextCount(4)
            .verifyComplete();
    }

    @Test
    public void sideEffects(){
    Flux<String>flux=fluxService.sideEffectFlux();
        System.out.println(flux);
        flux.subscribe(data->System.out.println(data));
    }
}
