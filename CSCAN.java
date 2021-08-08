// C-SCAN Disk Scheduling

import java.util.*;

public class CSCAN {
	private static int currentHead;
	private static ArrayList<Integer> headRequest = new ArrayList<>();
	private static int left = 0, right = 0, max = 100, min = 0;

	public static void main(String[] args) {
		System.out.println("---C-SCAN DISK SCHEDULING---");
		input();

		System.out.println("\nTotal Head Movement: " + schedule());
		graph();
	}

	public static void setCurrentHead(int head) {
		currentHead = head;
	}
	public static int getCurrentHead() {
		return currentHead;
	}

	public static void input() {
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);

		System.out.print("Current Head Position: ");
		setCurrentHead(sc1.nextInt());

		System.out.print("Read Request: ");
		String var = sc2.nextLine();

		String[] requests = var.split(" ");
		headRequest.add(getCurrentHead());

		for (int x = 0; x < requests.length; x++)
			headRequest.add(Integer.parseInt(requests[x]));
	}

	public static void arrangeAscending() {
		int cont;

		for (int x = 0; x < headRequest.size(); x++) {
			for (int y = x + 1; y < headRequest.size(); y++) {
				if (headRequest.get(x) > headRequest.get(y)) {
					cont = headRequest.get(x);
					headRequest.set(x, headRequest.get(y));
					headRequest.set(y, cont);
				}
			}
		}
	}

	// If the end is reached, it will continue its run to the other end of the range
	public static int schedule() {
		int total = 0;

		headRequest.add(max);
		headRequest.add(min);
		arrangeAscending();

		for (int x = 0; x < headRequest.size(); x++) {
			if (headRequest.get(x) == getCurrentHead()) {
				if (x != 0)
					left = Math.abs(headRequest.get(x) - headRequest.get(x - 1));

				if (x != headRequest.size() - 1)
					right = Math.abs(headRequest.get(x) - headRequest.get(x + 1));

				break;
			}
		}

		if (left < right) {
			arrangeSequence();
			headRequest.add(headRequest.remove(0));

			for (int x = headRequest.size(); x > 0; x--)
				headRequest.add(headRequest.remove(x - 1));

			total = (getCurrentHead() - min) + (max - headRequest.get(headRequest.size() - 1));
		} else if (left >= right) {
			arrangeSequence();
			total = (max - getCurrentHead()) + (headRequest.get(headRequest.size() - 1) - min);
		}

		return total;
	}

	public static void arrangeSequence() {
		for (int x = 0; x < headRequest.size(); x++) {
			if (headRequest.get(0) != getCurrentHead())
				headRequest.add(headRequest.remove(0));
			else
				break;
		}
	}

	// Customized graph using spaces
	public static void graph() {
		System.out.println();
		for (int x = 0; x <= max / 10; x++) {
			System.out.print(x * 10);

			for (int y = 0; y < (10 - String.valueOf(x * 10).length()); y++)
				System.out.print(" ");
		}
		System.out.println();

		for (int x = 0; x <= max / 10; x++) {
			System.out.print("|");

			for (int y = 0; y < 9; y++)
				System.out.print(" ");
		}
		System.out.println("\n");

		for (int x = 0; x < headRequest.size(); x++) {
			for (int y = 0; y < headRequest.get(x); y++)
				System.out.print(" ");

			System.out.println("*");
		}
	}
}