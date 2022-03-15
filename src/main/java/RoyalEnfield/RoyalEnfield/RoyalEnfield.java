package RoyalEnfield.RoyalEnfield;

import java.util.HashMap;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class RoyalEnfield {

	public static void main(String[] args) {
		SpringApplication.run(RoyalEnfield.class, args);
//		SpringApplication spr=new SpringApplication(RoyalEnfield.class); //2nd way 
//		System.out.println("Enter port no "); 
//		Scanner scan=new Scanner(System.in);
//		int port=scan.nextInt();//User will type the port no. For ex: 9060
//		HashMap<String, Object> portconf=new HashMap<>(); portconf.put("server.port", port);
//		spr.setDefaultProperties(portconf);
//		spr.run(args);
//		scan.close();
	}

}
