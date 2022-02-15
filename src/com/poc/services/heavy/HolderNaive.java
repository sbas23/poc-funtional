package com.poc.services.heavy;

public class HolderNaive {
	private Heavy heavy;
	
	public HolderNaive() {
		System.out.println("Titular creado");
	}
	
	public synchronized Heavy getHeavy() {
		if (heavy == null) {
			heavy = new Heavy();
		}
		return heavy;
	}
	
	public static void main(String[] args) {
		final HolderNaive holder = new HolderNaive();
		System.out.println("aplazando la creación pesada...");
		System.out.println(holder.getHeavy());
		System.out.println(holder.getHeavy());
	}
}
