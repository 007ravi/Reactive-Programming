package com.example.reactive.programming.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

@Service
public class FluxService {

    //creating flux
    public Flux<String>getFLuxNames(){
        Flux<String>nameFlux= Flux.just("Ravi","Shubham","Nikhil","Sanju");
        return nameFlux;
    }

    //flux intialize with iterable
    public Flux<String>getFruitsFLux(){
        List<String>fruits=List.of("Mango","Apple","Grapes");
        return Flux.fromIterable(fruits);
    }

    //empty flux
    public Flux<Void>getBlankFlux(){
        return Flux.empty();
    }

    //map
    public Flux<String>mapExampleFlux(){
        Flux<String>map=getFLuxNames().map(name->name.toUpperCase());
        return map;
    }

    //flatMap
    public Flux<String>flatMapExampleFlux() {

        return getFLuxNames().flatMap(name->
            Flux.just(name.split(""))).delayElements(Duration.ofMillis(2000));
    }

    //filter
    public Flux<String>filterExampleFlux(){
        return getFLuxNames().filter(name->name.length()>4).log();
    }

    


    //transform Example
    public Flux transformExample(){
        Function<Flux<String>,Flux<String>>funInterface=(name)->name.map(String::toUpperCase);
       return getFLuxNames().transform(funInterface).log();

    }

    //ifExample
    public  Flux ifExample(int length){
        return getFLuxNames()
                .filter(name->name.length()>length)
                .defaultIfEmpty("No elment length greater than "+length);
    }

    //switchExample

    //if filter condition fail for every data in flux the switchif empty will return the flux which is mentioned in it.
    public  Flux switchExample(int length){
        return getFLuxNames()
                .filter(name->name.length()>length)
                .switchIfEmpty(getFLuxNames());
    }


    //concat(static)/concatWith(instance)
    //synchronous way of combining two flux
    public Flux<String>concatExample(){
        return Flux.concat(getFLuxNames().delayElements(Duration.ofSeconds(2)),getFruitsFLux().delayElements(Duration.ofSeconds(1)));
      //  getFLuxNames().concatWith(getFruitsFLux()); same thing different implementation.
    }


    //merge and mergeWith
    //asynchronous way of combining two flux
    public Flux<String>mergeWithExample(){
        return Flux.merge(getFLuxNames().delayElements(Duration.ofMillis(2000)),getFruitsFLux().delayElements(Duration.ofMillis(1000)));
    }

    //zip and zipWith
    public Flux<Tuple2<String,Integer>> zipExample(){
            return  Flux.zip(getFLuxNames(),Flux.just(1,2,3,4,5));
    }

    public Flux<String> zipExample2(){
        return  Flux.zip(getFLuxNames(),Flux.just(1,2,3,4,5),(first,second)->{
            return first+" : "+second;
        });

    }

    //doonnext work before every next run test in test file to understand deeply
    public  Flux<String> sideEffectFlux(){
        return getFLuxNames().doOnNext(data->{
            System.out.println(data+"on Next");
        }).doOnSubscribe(data->{
            System.out.println(data+"on subscribe");
        });
    }


}
