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


public class CursorAction {
	public static String insert(String str, String str1) {
		String temp = str;
		int i = temp.indexOf("_");
		if (i > 0)
			return temp.substring(0, i) + str1
					+ temp.substring(i, str.length());
		else
			return str1 + temp;

	}

	public static String delete(String str) {
		int i = str.indexOf("_");
		if (i > 0)
			return str.substring(0, i - 1) + "_"
					+ str.substring(i + 1, str.length());
		else
			return str;
	}

	public static String move(String str, int mount) {
		String temp = str;
		int i = temp.indexOf("_");
		int j = i + mount;
		if (j < 0)
			j = 0;
		if (j > (temp.length() - 1))
			j = temp.length() - 1;
		if (mount > 0)
			return temp.substring(0, i) + temp.substring(i + 1, j + 1) + "_"
					+ temp.substring(j + 1, temp.length());
		else
			return temp.substring(0, j) + "_" + temp.substring(j, i)
					+ temp.substring(i + 1, temp.length());
	}

}
