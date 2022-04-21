package com.example.demo;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
public class FlightsController {

	@Autowired
	FlightsRepository repository;

	@GetMapping
	public ResponseEntity<List<Flight>> getAllFlights() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(repository.findAll());
	}

	@GetMapping(path = "/specific")
	public ResponseEntity<String> getSpecificFlight(@RequestBody String sobject) {
		JSONObject object = new JSONObject(sobject);
		Flight flight = repository.findByFlightNumberAndDate(object.get("flightNumber").toString(),
				DateFormatter.formatter(object.get("departureDate").toString()));

		String jsonString = new JSONObject().put("CargoWeight", flight.getCargoWeight())
				.put("BaggageWeight", flight.getBaggageWeight())
				.put("TotalWeight", flight.getCargoWeight() + flight.getBaggageWeight()).toString();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(jsonString);
	}

	@GetMapping(path = "/IATA")
	public ResponseEntity<String> findByIATA(@RequestBody String sobject) {
		JSONObject object = new JSONObject(sobject);
		List<Flight> flights = repository.findByIATACodeAndDate(object.get("AirportIATACode").toString(),
				DateFormatter.formatter(object.get("departureDate").toString()));
		int departing = 0;
		int arriving = 0;
		int baggageDeparting = 0;
		int baggageArriving = 0;

		for (Flight flight : flights) {
			if (flight.getArrivalAirportIATACode().equals(object.get("AirportIATACode").toString())) {
				arriving += 1;
				baggageArriving += flight.getBaggagePieces();
			}
			if (flight.getDepartureAirportIATACode().equals(object.get("AirportIATACode").toString())) {
				departing += 1;
				baggageDeparting += flight.getBaggagePieces();
			}
		}

		String jsonString = new JSONObject().put("Flights departing", departing).put("Flights arriving", arriving)
				.put("Baggage departing", baggageDeparting).put("Baggage arriving", baggageArriving).toString();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(jsonString);

	}

	@PostMapping
	public BodyBuilder addFlight(@RequestBody String sObject) {
		JSONArray array = new JSONArray(sObject);
		for (int i = 0; i < array.length(); i++) {
			Flight newFlight = new Flight(Integer.valueOf(array.getJSONObject(i).getInt("flightId")),
					array.getJSONObject(i).get("flightNumber").toString(), array.getJSONObject(i).get("departureDate"),
					array.getJSONObject(i).get("departureAirportIATACode").toString(),
					array.getJSONObject(i).get("arrivalAirportIATACode").toString());
			repository.save(newFlight);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED);
	}

	@PatchMapping
	public BodyBuilder addWeight(@RequestBody String sObject) {
		JSONArray array = new JSONArray(sObject);
		for (int i = 0; i < array.length(); i++) {
			Flight updated = repository.findFlightById(i);
			JSONObject obj = array.getJSONObject(i);
			JSONArray marray = obj.getJSONArray("baggage");
			for (int j = 0; j < marray.length(); j++) {
				updated.setBaggageWeight(marray.getJSONObject(j).getInt("weight"),
						marray.getJSONObject(j).get("weightUnit").toString());
				updated.setBaggagePieces(marray.getJSONObject(j).getInt("pieces"));
				repository.save(updated);
			}
			marray = obj.getJSONArray("cargo");

			for (int j = 0; j < marray.length(); j++) {
				updated.setCargoWeight(marray.getJSONObject(j).getInt("weight"),
						marray.getJSONObject(j).get("weightUnit").toString());
				updated.setCargoPieces(marray.getJSONObject(j).getInt("pieces"));
				repository.save(updated);
			}
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED);
	}
}