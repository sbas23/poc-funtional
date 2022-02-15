package com.poc.services.defaultmethods;

public class Main {
	public static void main(String[] args) {
		SeaPlane seaPlane = new SeaPlane();
		seaPlane.takeOff();
		seaPlane.turn();
		seaPlane.cruise();
		seaPlane.land();
	}
}
