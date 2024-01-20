import java.util.Scanner;

public class PalindromeChecker {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Prompt user to enter a string
		System.out.print("Enter a string: ");
		String input = scanner.nextLine();

		// Check if the input string is a palindrome
		if (isPalindrome(input)) {
			System.out.println("The string '"+input+"' is a palindrome.");
		} 
		else 
		{
            	System.out.println("The string '"+input+"' is not a palindrome.");
		}
	}

	// Function to check if a string is a palindrome
	private static boolean isPalindrome(String str) {
		// Remove spaces and convert to lowercase for case-insensitive comparison
		str = str.replaceAll("\\s", "").toLowerCase();

		// Compare characters from both ends of the string
		int leftIndex = 0;
		int rightIndex = str.length() - 1;

		while (leftIndex < rightIndex) {
			if (str.charAt(leftIndex) != str.charAt(rightIndex)) {
				return false; // Not a palindrome
			}
			leftIndex++;
			rightIndex--;
		}

		return true; // It's a palindrome
	}
}
