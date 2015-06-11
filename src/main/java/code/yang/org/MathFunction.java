/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package code.yang.org;

public class MathFunction {
	static final double sq2p1 = 2.414213562373095048802e0;
	static final double sq2m1 = .414213562373095048802e0;
	static final double p4 = .161536412982230228262e2;
	static final double p3 = .26842548195503973794141e3;
	static final double p2 = .11530293515404850115428136e4;
	static final double p1 = .178040631643319697105464587e4;
	static final double p0 = .89678597403663861959987488e3;
	static final double q4 = .5895697050844462222791e2;
	static final double q3 = .536265374031215315104235e3;
	static final double q2 = .16667838148816337184521798e4;
	static final double q1 = .207933497444540981287275926e4;
	static final double q0 = .89678597403663861962481162e3;
	static final double PIO2 = 1.5707963267948966135E0;
	static final double nan = (0.0 / 0.0);
	private final static String ESTR = String.valueOf(2.718281828459045);

	public static double ln(double x) {
		if (String.valueOf(x).equals(ESTR))
			return 1.0;
		if (x > 2) {
			double x1 = 1.0 / x;
			return -1 * ln(x1);
		}
		double total = 0;
		double flag = -1;
		for (int i = 1; i <= 10; i++) {
			flag *= -1;
			double t = 1;
			for (int j = 1; j <= i; j++) {
				t *= (x - 1);
			}
			t /= i;
			t *= flag;
			if (Math.abs(t) < 10E-6)
				t = 0;
			total += t;

		}
		if (Math.abs(total) < 10E-6)
			return 0;
		else
			return total;
	}

	public static double log10(double x) {
		double d = ln(x) / ln(10);
		if (Math.abs(d) < 10e-6)
			return 0;
		else
			return d;
	}

	public static double pow1(double d1, int d2) {
		double rt = 1;
		for (int i = 0; i < d2; i++) {
			rt *= d1;
		}
		return rt;
	}

	public static double pow2(double x, double y) {
		int den = 1000;
		int num = (int) (y * den);
		int s = (num / den) + 1;
		double z = Double.MAX_VALUE;
		while (z >= Double.MAX_VALUE) {
			den -= 1;
			num = (int) (y * den);
			s = (num / den) + 1;
			z = x;
			for (int i = 1; i < num; i++)
				z *= x;
		}
		double n = x;
		for (int i = 1; i < s; i++)
			n *= x;
		while (n > 0) {
			double a = n;
			for (int i = 1; i < den; i++)
				a *= n;
			double check1 = a - z;
			double check2 = z - a;
			if (check1 < .00001 || check2 > .00001)
				return n;
			n *= .999;
		}
		return -1.0;
	}

	public static long factorial(int n) {
		if (n == 0)
			return 1;
		long rt = 1;
		for (int i = 1; i <= n; i++) {
			rt *= i;
		}
		return rt;
	}

	private static double mxatan(double arg) {
		double argsq, value;

		argsq = arg * arg;
		value = ((((p4 * argsq + p3) * argsq + p2) * argsq + p1) * argsq + p0);
		value = value
				/ (((((argsq + q4) * argsq + q3) * argsq + q2) * argsq + q1)
						* argsq + q0);
		return value * arg;
	}

	// reduce
	private static double msatan(double arg) {
		if (arg < sq2m1)
			return mxatan(arg);
		if (arg > sq2p1)
			return PIO2 - mxatan(1 / arg);
		return PIO2 / 2 + mxatan((arg - 1) / (arg + 1));
	}

	// implementation of atan
	public static double arcTan(double arg) {
		if (arg > 0)
			return msatan(arg);
		return -msatan(-arg);
	}

	// implementation of atan2
	public static double arcTan2(double arg1, double arg2) {
		if (arg1 + arg2 == arg1) {
			if (arg1 >= 0)
				return PIO2;
			return -PIO2;
		}
		arg1 = arcTan(arg1 / arg2);
		if (arg2 < 0) {
			if (arg1 <= 0)
				return arg1 + Math.PI;
			return arg1 - Math.PI;
		}
		return arg1;

	}

	// implementation of asin
	public static double arcSin(double arg) {
		double temp;
		int sign;

		sign = 0;
		if (arg < 0) {
			arg = -arg;
			sign++;
		}
		if (arg > 1)
			return nan;
		temp = Math.sqrt(1 - arg * arg);
		if (arg > 0.7)
			temp = PIO2 - arcTan(temp / arg);
		else
			temp = arcTan(arg / temp);
		if (sign > 0)
			temp = -temp;
		return temp;
	}

	// implementation of acos
	public static double arcCos(double arg) {
		if (arg > 1 || arg < -1)
			return nan;
		return PIO2 - arcSin(arg);
	}

	public static boolean isOp(char ch) {
		switch (ch) {
		case '+':
		case '-':
		case '*':
		case '/':
		case '^':
		case '(':
			return true;
		default:
			return false;
		}
	}

	public static boolean isNum(char ch) {
		switch (ch) {
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case '0':
		case '.':
			return true;
		default:
			return false;
		}
	}
}
