package com.poc.services.heavy;

import java.util.function.Supplier;

public class Holder {

	private Supplier<Heavy> heavy = () -> createAndCacheHeavy();

	public Holder() {
		System.out.println("Titular creado");
	}

	public Heavy getHeavy() {
		return heavy.get();
	}

	private synchronized Heavy createAndCacheHeavy() {
		class HeavyFactory implements Supplier<Heavy> {
			private final Heavy heavyInstance = new Heavy();

			public Heavy get() {
				return heavyInstance;
			}
		}
		if (!HeavyFactory.class.isInstance(heavy)) {
			heavy = new HeavyFactory();
		}
		return heavy.get();
	}

	public static void main(final String[] args) {
		final Holder holder = new Holder();
		System.out.println("aplazando la creación pesada...");
		System.out.println(holder.getHeavy());
		System.out.println(holder.getHeavy());
	}
}
