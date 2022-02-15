package com.poc.services.general;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static java.util.Comparator.comparing;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

import com.poc.models.Person;

public class App {

	public static void main(String[] args) {
		boolean execute = true;
		if (execute) {
			compareAndProcess();
		} else {
			example1();
			filters(); // Uso de filtros
			pickName(); // busca en una lista
			pickALongest(); // busca en una lista
			joins(); // Uso de joins
			workChar(); // procesa cada caracter de un string
			compare(); // ordenar y comparar listas
		}
	}
	
	/** --------------------------------------------------------------------------------------------------------- */
	private static void compareAndProcess() {
		final List<Person> people = Arrays.asList(
				new Person("John", 20),
				new Person("Sara", 21),
				new Person("Jane", 21),
				new Person("Greg", 35),
				new Person("Juan", 10)
		);
		
		List<Person> olderThan20 = people.stream()
				.filter(person -> person.getAge() > 20)
				.collect(Collectors.toList());
		System.out.println(MessageFormat.format("Personas mayores a 20: {0} \n", olderThan20));
		//******************************************************************************************
		Map<Integer, List<Person>> peopleByAge = people.stream()
			.collect(Collectors.groupingBy(Person::getAge));
		System.out.println("Agrupacion por edad:" + peopleByAge + "\n");
		//******************************************************************************************
		Map<Integer, List<String>> nameofPeopleByAge = people.stream()
				.collect(groupingBy(Person::getAge, mapping(Person::getName, toList())));
		System.out.println(MessageFormat.format("Personas agrupadas por nombre: {0} \n", nameofPeopleByAge));
		//******************************************************************************************
		Comparator<Person> byAge = Comparator.comparing(Person::getAge);
		Map<Character, Optional<Person>> oldestPersonOfEachLetter = people.stream()
				.collect(groupingBy(person -> person.getName().charAt(0), reducing(BinaryOperator.maxBy(byAge))));
		System.out.println(MessageFormat.format("Personas mas vieja agrupada por primera letra: {0} \n", oldestPersonOfEachLetter));
	}

	/** --------------------------------------------------------------------------------------------------------- */
	private static void compare() {
		final List<Person> people = Arrays.asList(
				new Person("John", 20),
				new Person("Sara", 21),
				new Person("Jane", 21),
				new Person("Greg", 35),
				new Person("Juan", 10)
		);
		
		List<Person> ascendingAge =	people.stream()
					.sorted((person1, person2) -> person1.ageDifference(person2))
					.collect(toList());
		printPeople("Sorted in ascending order by age: ", ascendingAge);
		//******************************************************************************************
		Comparator<Person> compareAscending = (person1, person2) -> person1.ageDifference(person2);
		Comparator<Person> compareDescending = compareAscending.reversed();
		printPeople("Sorted in ascending order by age: ",
				people.stream()
				.sorted(compareAscending)
				.collect(toList())
		);
		printPeople("Sorted in descending order by age: ",
				people.stream()
				.sorted(compareDescending)
				.collect(toList())
		);
		//******************************************************************************************
		printPeople("Sorted in ascending order by name: ",
				people.stream()
				.sorted((person1, person2) -> person1.getName().compareTo(person2.getName()))
				.collect(toList())
		);
		//******************************************************************************************
		people.stream()
			.min(Person::ageDifference)
			.ifPresent(youngest -> System.out.println("Youngest: " + youngest)
		);
		people.stream()
			.max(Person::ageDifference)
			.ifPresent(eldest -> System.out.println("Eldest: " + eldest)
		);
		//******************************************************************************************
		final Function<Person, String> byName = person -> person.getName();
		final Function<Person, Integer> byAge = person -> person.getAge();
		
		printPeople("Sorted in ascending order by age and name: ",
			people.stream()
				.sorted(comparing(byAge).thenComparing(byName))
				.collect(toList())
		);
		
	}
	
	public static void printPeople(final String message, final List<Person> people) {
		System.out.println(message);
		people.forEach(System.out::println);
	}
	
	/** --------------------------------------------------------------------------------------------------------- */
	private static void workChar() {
		String name = "Sebastian123";
		name.chars().forEach(App::printChar);
		name.chars().mapToObj(ch -> Character.valueOf((char) ch)).forEach(System.out::println);
		name.chars().filter(ch -> Character.isDigit(ch)).forEach(ch -> printChar(ch));
		name.chars().filter(Character::isDigit).forEach(App::printChar);
	}

	private static void printChar(int aChar) {
		System.out.println((char) (aChar));
	}

	/** --------------------------------------------------------------------------------------------------------- */
	private static void joins() {
		final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
		System.out.println(String.join(",", friends));

		System.out.println(friends.stream().map(String::toUpperCase).collect(joining(", ")));
	}

	/** --------------------------------------------------------------------------------------------------------- */
	private static void filters() {
		final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

		/** part-zero */
		friends.stream().map(name -> name.length()).forEach(count -> System.out.println(count + " "));

		/** part-one */
		final List<String> startsWithN = friends.stream().filter(name -> name.startsWith("N"))
				.collect(Collectors.toList());
		System.out.println(String.format("Found %d names", startsWithN.size()));

		/** part-two */
		final Function<String, Predicate<String>> startsWithLetter = letter -> name -> name.startsWith(letter);

		final long countFriendsStartN = friends.stream().filter(startsWithLetter.apply("N")).count();
		final long countFriendsStartB = friends.stream().filter(startsWithLetter.apply("B")).count();
		System.out.println(String.format("Found %d names with N and found %d names with B", countFriendsStartN,
				countFriendsStartB));

	}
	
	/** --------------------------------------------------------------------------------------------------------- */
	public static void pickName() {
		final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

		final Optional<String> foundName = friends.stream().filter(name -> name.startsWith("X")).findFirst();
		System.out.println(String.format("A name starting with %s: %s", "N", foundName.orElse("No name found"))); // 50
	}

	/** --------------------------------------------------------------------------------------------------------- */
	public static void pickALongest() {
		final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

		final int total = friends.stream().mapToInt(name -> name.length()).sum();
		System.out.println(String.format("La suma del total de nombres es: %d", total));

		final Optional<String> aLongName = friends.stream()
				.reduce((name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
		aLongName.ifPresent(name -> System.out.println(String.format("A longest name: %s", name)));

		final String steveOrLonger = friends.stream().reduce("Steve",
				(name1, name2) -> name1.length() >= name2.length() ? name1 : name2);
		System.out.println(String.format("A longest name: %s", steveOrLonger));
	}
	
	/** --------------------------------------------------------------------------------------------------------- */
	private static void example1() {
		final List<BigDecimal> prices = Arrays.asList(new BigDecimal("10"), new BigDecimal("30"), new BigDecimal("17"),
				new BigDecimal("20"), new BigDecimal("15"), new BigDecimal("18"), new BigDecimal("45"),
				new BigDecimal("12"));

		final BigDecimal totalOfDisconuntedPrices = prices.stream()
				.filter(price -> price.compareTo(BigDecimal.valueOf(20)) > 0)
				.map(price -> price.multiply(BigDecimal.valueOf(0.9))) // multiplica dos numeros
				.reduce(BigDecimal.ZERO, BigDecimal::add); // reduce una lista a un solo elemento
		System.out.println(totalOfDisconuntedPrices);
	}

}
