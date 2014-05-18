package hyrax;

import java.math.BigInteger;
import java.util.ArrayList;
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
}
