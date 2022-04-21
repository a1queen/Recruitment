package com.example.demo;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@AllArgsConstructor
public class Flight {

	public Flight(Integer id, String flightNumber, Object date, String departureAirportIATACode,
			String arrivalAirportIATACode) {
		this.id = id;
		this.flightNumber = flightNumber;
		this.date = DateFormatter.formatter(date.toString());
		this.departureAirportIATACode = departureAirportIATACode;
		this.arrivalAirportIATACode = arrivalAirportIATACode;
	}

	@Id
	private final Integer id;
	private final String flightNumber;
	private final ZonedDateTime date;
	private double cargoWeight = 0;
	private double baggageWeight = 0;
	private int cargoPieces = 0;
	private int baggagePieces = 0;
	private final String departureAirportIATACode;
	private final String arrivalAirportIATACode;

	public void setCargoWeight(int weight, String unit) {
		if (unit.equals("lb"))
			cargoWeight += WeightConverter.convertToKgs(weight);
		else
			cargoWeight += weight;
	}

	public void setCargoPieces(int pieces) {
		cargoPieces += pieces;
	}

	public void setBaggageWeight(int weight, String unit) {
		if (unit.equals("lb"))
			baggageWeight += WeightConverter.convertToKgs(weight);
		else
			baggageWeight += weight;
	}

	public void setBaggagePieces(int pieces) {
		baggagePieces += pieces;
	}

}
