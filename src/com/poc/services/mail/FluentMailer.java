package com.poc.services.mail;

import java.util.function.Consumer;

public class FluentMailer {
	private static StringBuilder result = new StringBuilder();
	private FluentMailer() {}
	  
	public FluentMailer from(final String address) {
		result.append(address).append("|");
		return this;
	}

	public FluentMailer to(final String address) {
		result.append(address).append("|");
		return this;
	}

	public FluentMailer subject(final String line) {
		result.append(line).append("|");
		return this;
	}

	public FluentMailer body(final String message) {
		result.append(message).append("|");
		return this;
	}

	public static void send(final Consumer<FluentMailer> block) {
		final FluentMailer mailer = new FluentMailer();
		block.accept(mailer);
		System.out.println("sending...");
		
	}

	  //...
	  public static void main(final String[] args) {
	    FluentMailer.send(mailer ->
	      mailer.from("build@agiledeveloper.com")
	            .to("venkats@agiledeveloper.com")
	            .subject("build notification")
	            .body("...much better..."));
	    System.out.println(result);
	  }
}
