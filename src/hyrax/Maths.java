package hyrax;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Maths {
	
	/**
	 * Euclid's recursive algorithm for finding the greatest common divisor of two positive integers
	 * @param m first number
	 * @param n second number
	 * @return The GCD of m and n
	 */
	public static int gcd(int m, int n) {
		return (m % n == 0 ? n : gcd(n, m%n));
	}
	
	/**
	 * Proper 'Euclidean' implementation of the modulo operator that wraps correctly in the negative direction
	 * @param x dividend
	 * @param m divisor
	 * @return x % m, where the result is always positive
	 */
	public static int mod(int x, int m) {
		int r = x % m;
		return r < 0 ? r + m : r;
	}
	
	/**
	 * Finds the factorial of a number
	 * @param n 
	 * @return n!
	 */
	public static BigInteger factorial(int n) {
		BigInteger result = BigInteger.ONE;
		for (int i = 1; i <= n; i += 1) {
			result = result.multiply(new BigInteger(i + ""));
		}
		return result;
	}
	
	/**
	 * Naive primality test for reasonably small numbers
	 * @param n the number to test for prime-ness
	 * @return true if n is prime
	 */
	public static boolean isPrime(long n) {
		if (n < 2) {
			return false;
		}
		else if (n == 2 || n == 3) {
			return true;
		}
		else if (n % 2 == 0 || n % 3 == 0) {
			return false;
		}
		else {
			long top = (long) Math.floor(Math.sqrt(n));
			for (int k = 1; 6*k - 1 <= top; k += 1) {
				if ((n % (6*k + 1) == 0) 
				 || (n % (6*k - 1) == 0)) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Finds all prime numbers below a maximum using the Sieve of Eratosthenes
	 * @param n ceiling for primes - must be <= INT_MAXVALUE
	 * @return a List of prime integers less than n
	 */
	public static List<Integer> eratosthenes(int n) {
		boolean[] marks = new boolean[n+1];
		for (int i = 2; i < marks.length; i += 1) marks[i] = true;
		
		for (int p = 2; p <= n / p; p += 1) {
			if (marks[p]) {
				for (int i = p; i <= n / p; i += 1) {
					marks[i * p] = false;
				}
			}
		}
		
		List<Integer> results = new ArrayList<Integer>();
		for (int i = 2; i < marks.length; i += 1) {
			if (marks[i]) {
				results.add(i);
			}
		}
		return results;
	}
	
	/**
	 * Finds the nth fibonacci number.  Considers the first in the series to be 0,
	 * and is indexed starting at 1 (i.e., the 1st number is n = 1, the second is n = 2; there is no n = 0)
	 * @param n index of the number in the sequence to find
	 * @return the nth Fibonacci number
	 */
	public static long fibonacci(long n) {
		if (n < 1) {
			return -1;
		}
		else if (n == 1) {
			return 0;
		}
		else {
			long t = 0;
			long f1 = 0;
			long f2 = 1;
			for (int i = 3; i <= n; i += 1) {
				t = f2;
				f2 = f1 + f2;
				f1 = t;
			}
			return f2;
		}
	}
	
	/**
	 * Finds the number of k-combinations from a given set of n elements; C(n, k)
	 * @param n
	 * @param k
	 * @return C(n, k)
	 */
	public static long binomialCoefficient(int n, int k) {
		return new BinomialCoefficient().solve(n, k);
	}
	
	/**
	 * Convert a string representing a number in some base to a 
	 * string representing the same value in some other base.
	 * Preconditions: 
	 * - decimal value of input be less than Integer.MAX_VALUE
	 * - symbols in input not inconsistent with base specified
	 * - inBase and outBase between 2 and 36 
	 * @param input String representation of a number
	 * @param inBase the base of the number represented by "input"
	 * @param outBase the base to convert the number to
	 * @return input converted from inBase to outBase
	 */
	public static String convertBase(String input, int inBase, int outBase) {
		StringBuilder result = new StringBuilder();
		int decimal = 0;
		char[] symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		
		if (inBase > symbols.length || outBase > symbols.length) return "N/A";
	
		if (inBase == 10) {
			decimal = Integer.parseInt(input);
		}
		else {
			for (int i = 0; i < input.length(); i++) {
				decimal += (Arrays.binarySearch(symbols, input.charAt(i)) * (Math.pow(inBase, input.length()-i-1)));
			}
		}
		
		while (decimal > 0) {
			result.insert(0, symbols[decimal % outBase]);
			decimal /= outBase;
		}
		
		return result.toString();
	}
	
	/**
	 * Given some angle in whole degrees, return the same angle inside the range 0..360.
	 * For example, 400 -> 400 - 360 = 40, or -30 -> -30 + 360 = 330
	 * @param angle value in whole degrees
	 * @return the same angle, but in the range 0..360
	 */
	public static int normalizeDegrees(int angle) {
		int r = angle;
		if (angle >= 360) {
			return r % 360;
		}
		else if (angle < 0) {
			while (r < 0) {
				r += 360;
			}
		}
		return r;
	}
}

class BinomialCoefficient {
	private HashMap<Integer, Long> memo = new HashMap<Integer, Long>();
	public long solve(int n, int k) {
		int key = ((n * 31) + k);
		if (!memo.containsKey(key)) {
			long c;
			if (k == 0) {
				c = 1;
			}
			else if (n == 0 && k > 0) {
				c = 0;
			}
			else {
				c = solve(n - 1, k - 1) + solve(n - 1, k); 
			}
			memo.put(key, c);
		}
		return memo.get(key);
	}
}