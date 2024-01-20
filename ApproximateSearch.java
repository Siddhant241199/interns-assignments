import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ApproximateSearch {

	private static List<String> stringSet = new ArrayList<>();

	public static void main(String[] args) {
		loadStringSetFromFile("mystrings.txt");
		System.out.println("Welcome to the Approximate Search Program!");

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("Enter a word (or 'exit' to quit): ");
				String query = scanner.nextLine().toLowerCase();

				if (query.equals("exit")) {
					System.out.println("Exiting program. Goodbye!");
					break;
				}

				System.out.print("Enter the value of k for top-k results: ");
				int k = scanner.nextInt();
				scanner.nextLine(); 

				List<String> topKSimilarStrings = getTopKSimilarStrings(query, k);

				System.out.println("\nTop " + k + " similar strings to '" + query + "':");
				for (int i = 0; i < topKSimilarStrings.size(); i++) {
					System.out.println((i + 1) + ". " + topKSimilarStrings.get(i));
				}
            	}
		}
	}

	private static void loadStringSetFromFile(String filePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringSet.add(line.toLowerCase());
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error loading strings from file.");
		}
	}

	private static List<String> getTopKSimilarStrings(String query, int k) {
		Map<String, Integer> similarityMap = new HashMap<>();

		for (String str : stringSet) {
			int distance = calculate(query, str);
			similarityMap.put(str, distance);
		}

		List<Map.Entry<String, Integer>> sortedSimilarities = new ArrayList<>(similarityMap.entrySet());
		sortedSimilarities.sort(Comparator.comparing(Map.Entry::getValue));

		List<String> topKSimilarStrings = new ArrayList<>();
		for (int i = 0; i < Math.min(k, sortedSimilarities.size()); i++) {
			topKSimilarStrings.add(sortedSimilarities.get(i).getKey());
		}

		return topKSimilarStrings;
	}

	private static int calculate(String s1, String s2) {        //method to calculate levenstein distance
		int[][] dp = new int[s1.length() + 1][s2.length() + 1];

		for (int i = 0; i <= s1.length(); i++) {
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0) {
					dp[i][j] = j;
				} 
				else if (j == 0) {
					dp[i][j] = i;
				} 
				else {
					dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(s1.charAt(i - 1), s2.charAt(j - 1)),
					dp[i - 1][j] + 1,
					dp[i][j - 1] + 1);
				}
			}
		}

		return dp[s1.length()][s2.length()];
	}

	private static int costOfSubstitution(char a, char b) {
		return a == b ? 0 : 1;
	}

	private static int min(int... numbers) {
		return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
	}
}