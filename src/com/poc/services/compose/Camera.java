package com.poc.services.compose;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class Camera {

	private Function<Color, Color> filterClass;
	
	public Camera() {
		setFilters();
	}
	
	public Color capture(final Color inputColor) {
		return filterClass.apply(inputColor);
	}
	
	public void setFilters(final Function<Color, Color>... filters) {
		filterClass =
				Stream.of(filters)
					.reduce((filter, next) -> filter.compose(next))
					.orElseGet(Function::identity);
	}
	
	public static void main(String[] args) {
		final Camera camera = new Camera();
		
		final Consumer<String> printCaptured = filterInfo -> 
			System.out.println(String.format("Con %s: %s", filterInfo,
				camera.capture(new Color(200, 100, 200))));
	    
	    printCaptured.accept("filtro nulo");
	    
	    camera.setFilters(Color::brighter);
	    printCaptured.accept("filtro claro");
	    
	    camera.setFilters(Color::darker);
	    printCaptured.accept("filtro oscuro");
	    
	    camera.setFilters(Color::brighter, Color::darker);
	    printCaptured.accept("Claro & oscuro filtro");
	    
	}
	

}
