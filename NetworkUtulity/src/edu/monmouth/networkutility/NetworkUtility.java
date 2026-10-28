package edu.monmouth.networkutility;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NetworkUtility {

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		while (true) {
			printMenu();  // display option for user
			int choice = getUserChoice();

			// based on their choice execute appropriate method which handles all exceptions 
			switch (choice) {
			case 1 -> resolveDomainNameToIP();
			case 2 -> getDNSnameForIP();
			case 3 -> displayLocalHostInfo();
			case 4 -> checkReachability();
			case 5 -> {
				System.out.println("Exiting program");
				return;
			}
			default -> System.out.println("Invalid option. Please try again.");
			}
		}
	}

	private static void printMenu() {
		System.out.println("\n--- Network Utility Tool ---");
		System.out.println("1. Resolve Domain Name to IP");
		System.out.println("2. Retrieve DNS name given IP");
		System.out.println("3. Display Local Host Info");
		System.out.println("4. Determine Reachability");
		System.out.println("5. Exit");
		System.out.print("Choose an option: ");
	}

	private static int getUserChoice() {
		try {
			return Integer.parseInt(scanner.nextLine().trim());
		} catch (NumberFormatException e) {
			return -1; // Invalid input
		}
	}

	private static void resolveDomainNameToIP() {
		// prompt & retrieve domain name
		System.out.println("Enter a domain name: ");
		String domain = scanner.nextLine();

		try {
			// get the full set of IPs for this hostname
			InetAddress[] addresses = InetAddress.getAllByName(domain);

			System.out.println("Results for " + domain);

			for (int i=0; i < addresses.length; i++) {
				System.out.println("IP address: " + addresses[i].getHostAddress());
				System.out.println("Host name: " + addresses[i].getHostName());
				System.out.println("Fully Qualified Domain Name: " + addresses[i].getCanonicalHostName());
			}

		} catch (UnknownHostException e) {
			System.out.println("Could not get " + domain);
		}

	}

	private static void getDNSnameForIP() {
		// prompt & retrieve IP address

		System.out.print("Enter an IP address: ");
		String ip = scanner.nextLine().trim();

		try{
			// determine DNS name for the IP address

			InetAddress address = InetAddress.getByName(ip);
			System.out.println("DNS name for " + ip + ": " + address.getCanonicalHostName());

		} catch (UnknownHostException e) {
			System.out.println("Invalid IP address or Host: " + ip);
		}
	}

	private static void displayLocalHostInfo() {
		// print your local hosts IP address, hostname & fully qualifies domain name

		try {
			InetAddress local = InetAddress.getLocalHost();
			System.out.println("IP address: " + local.getHostAddress());
			System.out.println("Host name: " + local.getHostName());
			System.out.println("Fully Qualified Domain Name: " + local.getCanonicalHostName());
		} catch (UnknownHostException e) {
			System.out.println("Could not get local host info");
		}
	}

	private static void checkReachability() {
		// prompt & retrieve either a hostname or IP address

		System.out.println("Enter a hostname or Ip address: ");
		String input = scanner.nextLine();

		try {
			// determine if this is reachable within 3 seconds-print result either way1
			InetAddress address = InetAddress.getByName(input);
			if (address.isReachable(3000)) {
				System.out.println( input + "is reachable");
			} else {
				System.out.println(input + " is NOT reachable");
			}
		}	catch (UnknownHostException e ) {
			System.out.println("Unknown host: " + input);
		}	catch (IOException e) {
			System.out.println("Network error while checking " + input);
		}
	}
}