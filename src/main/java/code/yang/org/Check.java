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

public class Check {
  /**
   * check expression is empty or not
   * 
   * @param str
   * @return
   */
  public static boolean expNull(String str) {
    if (str == null || str == "")
      return false;
    return true;
  }

  /**
   * check brackets is matching or not
   * 
   * @param str
   * @return
   */
  public static boolean brackets(String str) {
    Stack<String> stack = new Stack<String>();
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      switch (ch) {
        case '(':
          stack.push(ch + "");
          break;
        case ')':
          if (stack.isEmpty())
            return false;
          stack.pop();
      }
    }
    if (stack.isEmpty())
      return true;
    return false;
  }

  /**
   * check divisor is zero or not
   * 
   * @param str
   * @return
   */

  public static boolean devidedByZero(String str) {
    int i = 1;
    while (i < str.length() - 1) {
      if (str.charAt(i) == '/') {
        i++;
        String temp = "";
        while (i < str.length() && MathFunction.isNum(str.charAt(i))) {
          temp += str.charAt(i) + "";
          i++;
        }
        double d = Double.parseDouble(temp);
        if (d == 0.0)
          return false;
      }
      i++;
    }
    return true;
  }

  /**
   * check if lack of number between two operators
   * 
   * @param str
   * @return
   */
  public static boolean opRepeat(String str) {
    if (str.charAt(0) != '-' && str.charAt(0) != '(' && MathFunction.isOp(str.charAt(0)))
      return false;// the first char is an operator
    if (MathFunction.isOp(str.charAt(str.length() - 1)))
      return false;// the last char is an operator
    int i = 1;
    while (i < str.length()) {
      if (MathFunction.isOp(str.charAt(i - 1)) && str.charAt(i) != '-' && str.charAt(i) != '('
          && MathFunction.isOp(str.charAt(i)))
        return false;
      i++;
    }
    return true;
  }
}
