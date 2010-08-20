package com.risetek.rismile.client.utils;

import java.util.ArrayList;

public class Utils {

	public static int RISMILE_TABLE_MAX_COLUMN_COUNT = 5;
	
	public static ArrayList<Integer> maoPao(ArrayList<Integer> columnIndex) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int[] x = new int[columnIndex.size()];
		for(int i=0;i<x.length;i++){
			x[i] = columnIndex.get(i);
		}
		for (int i = 0; i < x.length; i++) {
			for (int j = i + 1; j < x.length; j++) {
				if (x[i] > x[j]) {
					int temp = x[i];
					x[i] = x[j];
					x[j] = temp;
				}
			}
		}
		for (int i=0;i<x.length;i++) {
			result.add(x[i]);
		}
		return result;
	}
}
