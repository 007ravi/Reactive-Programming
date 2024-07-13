package com.example.reactive.programming;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;

@SpringBootTest
public class MonoTest {

    @Test
    void contextLoads() {
        System.out.println("Test started");
    }

    @Test
    public void workingWithMono(){
        //created Error mono for chaining with other mono using then


        Mono<Object> errorMono=Mono.error(new RuntimeException("Error !!"));
        errorMono.subscribe(System.out::println);

        //created mono
        Mono<Object>m1= Mono.just("ravi")
                .log()///used to log the process
                .then(errorMono);



        //1st way to consume the mono by subscribing
        System.out.println("1st way of getting data with mono \n m1.subscribe(data->{System.out.println(data);});");
        m1.subscribe(data->{System.out.println(data);});
        //2nd way to get data
        System.out.println("2nst way of getting data with mono\n m1.subscribe(System.out::println);");
        m1.subscribe(System.out::println);





    }

    @Test
    public void workingWithMonoZip(){

        System.out.println("Method workingWithMonoZip started :");
        Mono<String>m1=Mono.just("Ravi m1 data");
        Mono<String>m2=Mono.just("Ravi m2 Data");
        Mono<Tuple2<String,String>> combinedMonoT2=Mono.zip(m1,m2);

        //first way to get both mono data at same time
        combinedMonoT2.subscribe(data->{System.out.println(data);});

        //second way to get both mono data separately
        combinedMonoT2.subscribe(data->{
            System.out.println(data.getT1());
            System.out.println(data.getT2());
        });

        System.out.println("Zip with T3 started");
        Mono<String>m3=Mono.just("Ravi m3 Data");
        Mono<Tuple3<String,String,String>>combinedMonoT3=Mono.zip(m1,m2,m3);
        combinedMonoT3.subscribe(data->{
            System.out.println(data.getT1());
            System.out.println(data.getT2());
            System.out.println(data.getT3());
        });

    }

    @Test
    public void workingWithMonoMap(){
        //transform the value emmitted by current mono using sync function

        System.out.println("Method workingWithMonoMap started :");
        System.out.println("transform the value emmited by current mono using sync function");

        Mono<String>m1=Mono.just("Ravi m1 data");
        Mono<String>m2=Mono.just("Ravi m2 Data");
        Mono<String>resultMapMOno=m1.map(data->data.toLowerCase());
        resultMapMOno.subscribe(System.out::println);


        //concat with function to concat 2 mono which return flux
        System.out.println("Conact With m1 and m2 running");
        Flux<String >fluxConcat=m1
                .concatWith(m2)
                .log()
                .delayElements(Duration.ofMillis(2000));
        fluxConcat.subscribe(data->{
            System.out.println(data);
        });


    }

    @Test
    public void workingWithMonoDelayElement() throws InterruptedException {

        System.out.println("Method workingWithMonoMap started :");
        System.out.println("transform the value emmited by current mono using sync function");

        Mono<String>m1=Mono.just("Ravi m1 data");
        Mono<String>m2=Mono.just("Ravi m2 Data");


        //concat with function to concat 2 mono which return flux
        System.out.println("Conact With m1 and m2 running");
        Flux<String >fluxConcat=m1
                .concatWith(m2)
                .log()
                .delayElements(Duration.ofMillis(2000)); //will execute with other thread but the main thread should be running to get it executed or view its output in terminal


        fluxConcat.subscribe(data->{
            System.out.println(data);
            System.out.println(Thread.currentThread().getName());
        });
        Thread.sleep(3000);

        System.out.println("Main Thread Terminated"+Thread.currentThread());

    }

    @Test
    public void workingWithMonoFlatMap(){
        //transform the value emmitted by current mono using async function

        System.out.println("Method workingWithMonoFlatMap started :");
        System.out.println("transform the value emmited by current mono using async function");

        Mono<String>m1=Mono.just("Ravi m1 data");
        Mono<String>m2=Mono.just("Ravi m2 Data");

        Mono<String[]>resultFlatMapMono1=m1.flatMap(data->Mono.just(data.split(" ")));
        resultFlatMapMono1.subscribe(data->{
            for(String s:data){
                System.out.println(s);
            }
        });


        Mono<String>resultFlatMapMono2=m1.flatMap(data->Mono.just(data.toUpperCase()));
        resultFlatMapMono2.subscribe(System.out::println);
    }

    @Test
    public void workingWithMonoFlatMapMany(){
        //transform the value emmitted by current mono using async function
        //returns flux

        System.out.println("Method workingWithMonoFlatMapMany started :");
        System.out.println("transform the value emmited by current mono using async function");

        Mono<String>m1=Mono.just("Ravi m1 data");
        Mono<String>m2=Mono.just("Ravi m2 Data");

        Flux<String> resultFlatMapMono1=m1.flatMapMany(data->Flux.just(data.split(" "))).log();
        resultFlatMapMono1.subscribe(data->{
                System.out.println(data);
        });
    }

}
