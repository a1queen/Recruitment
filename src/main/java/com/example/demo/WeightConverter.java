package com.example.demo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class WeightConverter {

	public static double convertToKgs(int lbs) {
		return lbs*0.45359237;
	}
	public static double convertToLbs(int kgs) {
		return kgs/0.45359237;
	}
}
