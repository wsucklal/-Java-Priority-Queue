package Warren_Lab07;

import java.util.Scanner;


public class Warren_Lab07 {

	public static void main(String[] args) {
		String patient;
		String reservation;
		
		
		Scanner input = new Scanner(System.in);
		
		SortedPriorityQueue<Integer,String> list = new SortedPriorityQueue<Integer,String>();
		

		
							for(int i = 1; i<=5;i++) {
		
		System.out.println("Patient Name:");
		patient = input.nextLine();
		System.out.println("Reservation:(regular / emergency #)");
		reservation = input.nextLine();
		System.out.println("");

		list.insert(list.sort(reservation),patient);
		}
		
		list.print();
		
		while(!(list.isEmpty())) {
			list.removeMin();
		}
		
	}

}
