import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ArithmeticExpressionEvaluator {

	public static void main(String[] args) {
		String inputFile = "input.txt";  
		String outputFile = "result.txt";  

		try {
			solveArithmeticExpressions(inputFile, outputFile);
			System.out.println("Expressions solved and results written to " + outputFile);
		} 
		catch (IOException e) {
			System.err.println("Error reading/writing file: " + e.getMessage());
		} 
		catch (ScriptException e) {
			System.err.println("Error evaluating expression: " + e.getMessage());
		}
	}

	private static void solveArithmeticExpressions(String inputFile, String outputFile) throws IOException, ScriptException {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("=", 2);
				if (parts.length == 2) {
					String expression = parts[0].trim();
					expression = expression.replaceAll("\\s", "");  //remove all spaces from the expression
					expression = checkForStar(expression);
					expression = checkForCapOperator(expression);
					String result = evaluateExpression(scriptEngine, expression);
					writer.write(line.replace(line,line+" "+result));
					writer.newLine();
				}
			}
        	}
    }

	private static String checkForStar(String expression){

		expression = expression.replaceAll("\\)\\(", ")*(");
		
		return expression;
	}

	private static String checkForCapOperator(String expression) {
		// Use regex to find and replace '^' with '**' for power operator
		expression = expression.replaceAll("\\^", "**");

		// Use regex to extract and replace power expressions
		Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*\\*\\*\\s*(\\d+(\\.\\d+)?)");
		Matcher matcher = pattern.matcher(expression);

		while (matcher.find()) {
			double base = Double.parseDouble(matcher.group(1));
			double exponent = Double.parseDouble(matcher.group(3));
			double result = Math.pow(base, exponent);
			expression = expression.replace(matcher.group(), Long.toString((long)result));
		}

		return expression;
	}


	private static String evaluateExpression(ScriptEngine scriptEngine, String expression) throws ScriptException {
		Object result = scriptEngine.eval(expression);
		return result.toString();
	}
}
