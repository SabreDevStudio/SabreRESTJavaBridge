package com.cometari.sabreapibridge.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.cometari.sabreapibridge.domain.Airport;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.operation.distance.DistanceOp;

@Service
public class AirportService {

	private List <Airport> airports = new ArrayList<Airport>();
	private List <String> airportsOrginalList = new ArrayList<String>();
	private List <String> airportsStandardizedList = new ArrayList<String>();	
	private GeometryFactory geometryFactory = new GeometryFactory();
	private MultiPoint coordinates;
	
	@PostConstruct
	void loadStream() throws IOException{		
		try (Stream<String> stream = Files.lines(Paths.get(new ClassPathResource("airports.csv").getFile().getPath()), StandardCharsets.UTF_8)) {
			stream.forEach(s -> {
				airportsOrginalList.add(s);	
				airportsStandardizedList.add(s.toLowerCase());
			});	
		}
	}
	
	@PostConstruct
	void loadCoordiate() throws IOException {		
		List<Point> coordiateList = new ArrayList<Point>();
		try (Stream<String> stream = Files.lines(Paths.get(new ClassPathResource("airports.csv").getFile().getPath()), StandardCharsets.UTF_8)) {
			stream.forEach(s ->{
				String[] result = s.split(";");	
				airports.add(new Airport(result[0], result[1], result[2], result[3], result[4], result[5]));	
				coordiateList.add(geometryFactory.createPoint(new Coordinate(Double.valueOf(result[4]),Double.valueOf(result[5]))));
			});		
			coordinates = geometryFactory.createMultiPoint(coordiateList.toArray(new Point[coordiateList.size()]));
		}		
	}
	
	public List<Airport> findAirportsByText(String text) throws IOException {
			List<String> searchResults = airportsStandardizedList.stream().parallel().filter(s -> s.contains(text)).collect(Collectors.toList());
			airportsStandardizedList.stream().parallel().close();
			if (!searchResults.isEmpty()) {
				List<Airport> airportList = new ArrayList<Airport>();
				searchResults.forEach(s -> {
					String[] result = airportsOrginalList.get(airportsStandardizedList.indexOf(s)).split(";");
					airportList.add(new Airport(result[0], result[1], result[2], result[3], result[4], result[5]));
				});
				return airportList;
			}		
		return Collections.emptyList();
	}	
		
	public Airport findAirportsByCoordiate(String lat, String lng) throws IOException {
		Point searchCoordinates = geometryFactory.createPoint(new Coordinate(Double.valueOf(lat), Double.valueOf(lng)));
		Coordinate[] nearestCoordinates = DistanceOp.nearestPoints(searchCoordinates, coordinates);
		Optional<Airport> searchResults = airports.stream().parallel().filter(s -> nearestCoordinates[1].equals(new Coordinate(Double.valueOf(s.getLatitude()), Double.valueOf(s.getLongitude())))).findFirst();
		airports.stream().parallel().close();
		if (searchResults.isPresent()) {
			return searchResults.get();
		}
		return new Airport();

	}

}
