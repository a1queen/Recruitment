package com.example.demo;

import java.time.ZonedDateTime;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class DateFormatter {
	public static ZonedDateTime formatter(String date) {
		return ZonedDateTime.parse(date);
	}
}
