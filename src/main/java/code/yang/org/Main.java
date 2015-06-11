/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package code.yang.org;

import java.util.Stack;

public class Main {

  public static String preHandle(String str) {
    if (str == null)
      return "";
    String result = str.charAt(0) + "";
    int i = 1;
    while (i < str.length()) {
      if (MathFunction.isOp(str.charAt(i - 1)) && str.charAt(i) == '-') {
        result += "(0-";
        i++;
        while (i < str.length() && MathFunction.isNum(str.charAt(i))) {
          result += str.charAt(i) + "";
          i++;
        }
        result += ")";
      } else {
        result += str.charAt(i) + "";
        i++;
      }
    }

    if (result.charAt(0) == '-')
      result = "0" + result;

    return result;
  }

  /**
   * convert infix expression into postfix expression
   * 
   * @param expstr
   * @return
   */
  public static String toPostfix(String expstr) {
    Stack<String> stack = new Stack<String>();
    String postfix = "";
    int i = 0;
    while (i < expstr.length()) {
      char ch = expstr.charAt(i);
      switch (ch) {
        case '+':
        case '-':
          while (!stack.isEmpty() && !stack.peek().equals("("))
            postfix += stack.pop();
          stack.push(ch + "");
          i++;
          break;
        case '*':
        case '/':
          while (!stack.isEmpty() && (stack.peek().equals("*") || stack.peek().equals("/")))
            postfix += stack.pop();
          stack.push(ch + "");
          i++;
          break;
        case '(':
          stack.push(ch + "");
          i++;
          break;
        case ')':
          String out = stack.pop();
          while (out != null && !out.equals("(")) {
            postfix += out;
            out = stack.pop();
          }
          i++;
          break;
        default:
          while (ch >= '0' && ch <= '9' || ch == '.') {
            postfix += ch;
            i++;
            if (i < expstr.length())
              ch = expstr.charAt(i);
            else
              ch = '=';
          }
          postfix += " ";
      }// switch
    }// while
    while (!stack.isEmpty())
      postfix += stack.pop();
    return postfix;
  }// fun

  public static double calculate(String postfix) {
    Stack<Double> stack = new Stack<Double>();
    int i = 0;
    double result = 0;
    while (i < postfix.length()) {
      char ch = postfix.charAt(i);
      if (ch >= '0' && ch <= '9' || ch == '.') {
        boolean dotIsAppeared = false;
        double j = 0.1;
        result = 0;
        while (ch != ' ') {
          if (ch != '.') {
            if (!dotIsAppeared) {// Decimal point has not occurred ,Processing integer part
              result = result * 10 + Double.parseDouble(ch + "");
              i++;
              ch = postfix.charAt(i);
            } else {// Decimal point has occurred ,Processing fractional part
              result = result + j * (Double.parseDouble(ch + ""));
              j *= 0.1;
              i++;
              ch = postfix.charAt(i);
            }

          } else {
            dotIsAppeared = true;
            j = 0.1;
            i++;
            ch = postfix.charAt(i);
          }
        }
        i++;
        stack.push(result);
      } else {
        double y = stack.pop();
        double x = stack.pop();
        switch (ch) {
          case '+':
            result = x + y;
            break;
          case '-':
            result = x - y;
            break;
          case '*':
            result = x * y;
            break;
          case '/':
            try {
              result = x / y;
            } catch (ArithmeticException e) {
              System.err.println("Divisor can not be zero");
              System.exit(-1);
            }
            break;
        }// switch
        stack.push(result);
        i++;
      }// else
    }// while
    return stack.pop();
  }

  public static double triFuction(String str, String str1) {
    int j = str.indexOf(str1);
    String rt = "";
    rt += str.substring(0, j);
    int rcount = 1;
    j = j + str1.length() + 1;
    char ch = str.charAt(j);
    String temp = "";
    while (rcount != 0) {
      if (ch == '(')
        rcount++;
      if (ch == ')')
        rcount--;
      if (rcount != 0) {
        temp = temp + (ch + "");
        j++;
        ch = str.charAt(j);
      }// if
    }// while
    double d = 0;
    if (str1.equals("sin"))
      d = Math.sin(count(temp) * Math.PI / 180);
    else if (str1.equals("cos"))
      d = Math.cos(count(temp) * Math.PI / 180);

    else if (str1.equals("tan"))
      d = Math.tan(count(temp) * Math.PI / 180);

    else if (str1.equals("arcSin"))
      d = MathFunction.arcSin(count(temp)) * 180 / Math.PI;

    else if (str1.equals("arcCos"))
      d = MathFunction.arcCos(count(temp)) * 180 / Math.PI;
    else if (str1.equals("arcTan"))
      d = MathFunction.arcTan(count(temp)) * 180 / Math.PI;
    if (Math.abs(d) < (10e-6))
      d = 0;
    if (d < 0) {
      String tt = String.valueOf(d);
      temp = "(0-" + tt.substring(1, tt.length()) + ")";
    } else
      temp = String.valueOf(d);
    rt += temp;
    j++;
    if (j < str.length())
      rt += str.substring(j);
    return count(rt);
  }

  public static boolean isNumOrDot(char arg) {
    switch (arg) {
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
      case '.':
        return true;
      default:
        return false;

    }
  }

  /**
   * Calculation expression
   * 
   * @param str
   * @return
   */
  public static double count(String str) {
    str = preHandle(str);
    double result = 0;
    int j = 0;
    String temp = "";
    String rt = "";
    int rcount = 1;
    if (str.indexOf("sin") < 0 && str.indexOf("cos") < 0 && str.indexOf("tan") < 0
        && str.indexOf("arcSin") < 0 && str.indexOf("arcCos") < 0 && str.indexOf("arcTan") < 0
        && str.indexOf("PI") < 0 && str.indexOf("sqrt") < 0 && str.indexOf("!") < 0
        && str.indexOf("^") < 0 && str.indexOf("ln") < 0 && str.indexOf("log") < 0
        && str.indexOf("e") < 0) {
      temp = toPostfix(str);
      result = calculate(temp);
    } else if (str.indexOf("sin") >= 0) {
      result = triFuction(str, "sin");
    } else if (str.indexOf("cos") >= 0) {
      result = triFuction(str, "cos");
    } else if (str.indexOf("tan") >= 0) {
      result = triFuction(str, "tan");
    } else if (str.indexOf("arcSin") >= 0) {
      result = triFuction(str, "arcSin");
    } else if (str.indexOf("arcCos") >= 0) {
      result = triFuction(str, "arcCos");
    } else if (str.indexOf("arTan") >= 0) {
      result = triFuction(str, "arcTan");
    } else if (str.indexOf("PI") >= 0) {
      j = str.indexOf("PI");
      rt = "";
      if (j + 2 < str.length())
        rt = str.substring(0, j) + String.valueOf(Math.PI) + str.substring(j + 2);
      else
        rt = str.substring(0, j) + String.valueOf(Math.PI);
      result = count(rt);

    } else if (str.indexOf("e") >= 0) {
      j = str.indexOf("e");
      rt = "";
      if (j + 1 < str.length())
        rt = str.substring(0, j) + Math.E + str.substring(j + 1);
      else
        rt = str.substring(0, j) + Math.E;
      result = count(rt);

    } else if (str.indexOf("sqrt") >= 0) {
      j = str.indexOf("sqrt");
      rt = "";
      rt += str.substring(0, j);
      rcount = 1;
      j += 5;
      char ch = str.charAt(j);
      temp = "";
      while (rcount != 0) {
        if (ch == '(')
          rcount++;
        if (ch == ')')
          rcount--;
        if (rcount != 0) {
          temp = temp + (ch + "");
          j++;
          ch = str.charAt(j);
        }// if
      }// while
      double d = Math.sqrt(count(temp));
      if (d < 0) {
        String tt = String.valueOf(d);
        temp = "(0-" + tt.substring(1, tt.length()) + ")";
      } else
        temp = String.valueOf(d);
      rt += temp;
      j++;
      if (j < str.length())
        rt += str.substring(j, str.length());
      result = count(rt);
    } else if (str.indexOf("log") >= 0) {
      j = str.indexOf("log");
      rt = "";
      rt += str.substring(0, j);
      rcount = 1;
      j += 4;
      char ch = str.charAt(j);
      temp = "";
      while (rcount != 0) {
        if (ch == '(')
          rcount++;
        if (ch == ')')
          rcount--;
        if (rcount != 0) {
          temp = temp + (ch + "");
          j++;
          ch = str.charAt(j);
        }// if
      }// while
      double d = MathFunction.log10(count(temp));
      if (d < 0) {
        String tt = String.valueOf(d);
        temp = "(0-" + tt.substring(1, tt.length()) + ")";
      } else
        temp = String.valueOf(d);
      rt += temp;
      j++;
      if (j < str.length())
        rt += str.substring(j, str.length());
      result = count(rt);
    } else if (str.indexOf("ln") >= 0) {
      j = str.indexOf("ln");
      rt = "";
      rt += str.substring(0, j);
      rcount = 1;
      j += 3;
      char ch = str.charAt(j);
      temp = "";
      while (rcount != 0) {
        if (ch == '(')
          rcount++;
        if (ch == ')')
          rcount--;
        if (rcount != 0) {
          temp = temp + (ch + "");
          j++;
          ch = str.charAt(j);
        }// if
      }// while
      double d = MathFunction.ln(count(temp));//
      if (d < 0) {
        String tt = String.valueOf(d);
        temp = "(0-" + tt.substring(1, tt.length()) + ")";
      } else
        temp = String.valueOf(d);
      rt += temp;
      j++;
      if (j < str.length())
        rt += str.substring(j, str.length());
      result = count(rt);
    } else if (str.indexOf("!") >= 0) {
      j = str.indexOf("!");
      String str1 = "";
      int i = j - 1;
      rt = "";
      char ch = str.charAt(i);
      if (ch != ')') {
        while (isNumOrDot(ch) && i >= 0) {
          if (i == 0) {
            i--;
          } else {
            i--;
            ch = str.charAt(i);
          }
        }
        i++;
        str1 = str.substring(i, j);
      }
      rt =
          str.substring(0, i) + String.valueOf(MathFunction.factorial(Integer.parseInt(str1)))
              + str.substring(j + 1, str.length());
      result = count(rt);
    } else if (str.indexOf("^") >= 0) {
      j = str.indexOf("^");
      String str1 = "", str2 = "";
      int i = j - 1;
      rt = "";
      char ch = str.charAt(i);
      if (ch != ')') {
        while (isNumOrDot(ch) && i >= 0) {
          if (i == 0) {
            i--;
          } else {
            i--;
            ch = str.charAt(i);
          }
        }
        i++;
        str1 = str.substring(i, j);
      }
      int k = j + 1;
      ch = str.charAt(k);
      if (ch != '(') {
        while (isNumOrDot(ch) && k <= (str.length() - 1)) {
          if (k == str.length() - 1) {
            k++;
          } else {
            k++;
            ch = str.charAt(k);
          }
        }
        str2 = str.substring(j + 1, k);
      }
      if (str2.indexOf('.') < 0)// Index is an integer
        rt =
            str.substring(0, i)
                + String
                    .valueOf(MathFunction.pow1(Double.parseDouble(str1), Integer.parseInt(str2)));
      else
        // Index is a decimal
        rt =
            str.substring(0, i)
                + String.valueOf(MathFunction.pow2(Double.parseDouble(str1),
                    Double.parseDouble(str2))) + str.substring(k, str.length());
      result = count(rt);
    } else if (str.indexOf("^2") >= 0) {
      j = str.indexOf("^");
      String str1 = "";
      int i = j - 1;
      rt = "";
      char ch = str.charAt(i);
      if (ch != ')') {
        while (isNumOrDot(ch) && i >= 0) {
          if (i == 0) {
            i--;
          } else {
            i--;
            ch = str.charAt(i);
          }
        }
        i++;
        str1 = str.substring(i, j);
      }

      rt =
          str.substring(0, i) + String.valueOf(MathFunction.pow1(Double.parseDouble(str1), 2))
              + str.substring(j + 1, str.length());
      result = count(rt);
    }

    return result;
  }// count

}// class
