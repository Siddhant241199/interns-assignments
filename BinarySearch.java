import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {

	public static int search(int[] array, int target) {
		int leftIndex = 0;
		int rightIndex = array.length - 1;

		while (leftIndex <= rightIndex) {
			int midIndex = leftIndex + (rightIndex - leftIndex) / 2;

			if (array[midIndex] == target) {
				return midIndex; // Element found
			} 
			else if (array[midIndex] < target) {
				leftIndex = midIndex + 1; // Search in the right half
			} 
			else {
				rightIndex = midIndex - 1; // Search in the left half
			}
		}

		return -1; // Element not found
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Input sorted array
		System.out.print("Enter the sorted array (comma-separated): ");
		String[] inputArray = scanner.nextLine().split(",");
		int[] sortedArray = Arrays.stream(inputArray).mapToInt(Integer::parseInt).toArray();

		// Input target element
		System.out.print("Enter the target element: ");
		int target = scanner.nextInt();

		// Perform binary search
		int result = search(sortedArray, target);

		// Display result
		if (result != -1) {
			System.out.println("Element "+target+" found at index " + result);
		} 
		else {
			System.out.println("Element "+target+" not found in the array.");
		}
	}
}