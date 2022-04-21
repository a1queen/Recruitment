package com.example.demo;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightsRepository extends JpaRepository<Flight, Integer> {
	Flight findByFlightNumberAndDate(String flightNumber, ZonedDateTime date);

	Flight findFlightById(Integer id);

	@Query("SELECT f FROM Flight f WHERE f.departureAirportIATACode = :name AND f.date = :date OR f.arrivalAirportIATACode = :name AND f.date = :date")
	List<Flight> findByIATACodeAndDate(@Param("name") String name, @Param("date") ZonedDateTime date);
}
