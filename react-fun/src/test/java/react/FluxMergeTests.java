package react;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

public class FluxMergeTests {

	@Test
	public void mergeFluxes(){
		
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa")
				.delayElements(Duration.ofMillis(500));
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples")
				.delaySubscription(Duration.ofMillis(250))//it won't emit any data until 250 ms 
				.delayElements(Duration.ofMillis(500)); //emitting every 500millsec
		
		Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);
		 StepVerifier.create(mergedFlux)
		 			 .expectNext("Garfield")
					 .expectNext("Lasagna")
					 .expectNext("Kojak")
					 .expectNext("Lollipops")
					 .expectNext("Barbossa")
					 .expectNext("Apples")
					 .verifyComplete();
				
	}
	
	@Test
	public void zipFluxes(){
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
	
		Flux<Tuple2<String,String>> zippedFlux = Flux.zip(characterFlux, foodFlux);
	
		StepVerifier.create(zippedFlux)
			.expectNextMatches( p -> 
					p.getT1().equals("Garfield")&&
					p.getT2().equals("Lasagna"))
			.expectNextMatches( p -> 
					p.getT1().equals("Kojak") &&
					p.getT2().equals("Lollipops"))
			.expectNextMatches( p -> 
					p.getT1().equals("Barbossa") &&
					p.getT2().equals("Apples"))
			.verifyComplete();
			
	}
	
	@Test
	public void zipFluxToObject(){
		Flux<String> characterFlux = Flux
				.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux
				.just("Lasagna", "Lollipops", "Apples");
		Flux<String> zippedFlux =
				Flux.zip(characterFlux, foodFlux, (c,f) -> c+" eats "+f);
		StepVerifier.create(zippedFlux)
					 .expectNext("Garfield eats Lasagna")
					 .expectNext("Kojak eats Lollipops")
					 .expectNext("Barbossa eats Apples")
					 .verifyComplete();
	}
	
	@Test
	public void firstFlux(){
		Flux<String> slowFlux = Flux
				.just("tortoise", "snail", "sloth")
				.delaySubscription(Duration.ofMillis(100));
		Flux<String> fastFlux = Flux
				.just("hare", "cheetah", "squirrel");
		Flux<String> firstFlux = Flux.first(slowFlux,fastFlux);
		StepVerifier.create(firstFlux)
					.expectNext("hare")
					.expectNext("cheetah")
					.expectNext("squirrel")
					.verifyComplete();
		
	}
	
	
	
}
