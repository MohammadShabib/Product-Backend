package com.rest;





import org.apache.commons.cli.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

;

@SpringBootApplication
public class ProductApiApplication {

	public static void main(String[] args) throws ParseException {


		Options options = new Options();
		options.addOption("h", "host", true, "Server hostname (default: localhost)");
		options.addOption("p", "port", true, "Server port (default: 3000)");

		// parse the command line args
		CommandLineParser parser = new PosixParser();
		CommandLine cl = parser.parse(options, args, false);

		SpringApplication.run(ProductApiApplication.class, args);

	}

}
