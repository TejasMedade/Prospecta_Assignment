/**
 * 
 */
package com.masai.controllers;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.masai.exceptions.ResourceNotFoundException;
import com.masai.modeldto.Apidata;
import com.masai.modeldto.EntryDetailsResponseDto;
import com.masai.modeldto.EntryResponseDto;
import com.masai.models.Entry;
import com.masai.payloads.ApiResponse;
import com.masai.services.EntryServices;

/**
 * @author ASUS
 *
 */
@RestController
public class EntryController {

	@Autowired
	private EntryServices entryServices;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ModelMapper modelMapper;

	// This API will list all the Entries
	// http:localhost:8088/entries

	@GetMapping("/entries")
	public ResponseEntity<List<Entry>> getEntriesHandler() {

		Apidata data = restTemplate.getForObject("https://api.publicapis.org/entries", Apidata.class);

		List<Entry> apiEntries = data.getEntries();
		
		

		List<EntryDetailsResponseDto> collect = apiEntries.stream()
				.map(e -> this.modelMapper.map(e, EntryDetailsResponseDto.class)).collect(Collectors.toList());

		return new ResponseEntity<List<Entry>>(apiEntries, HttpStatus.OK);
	}

	// Enter a category as a Request Parameter to get Entries based on Category
	// For Example : List All Entries With title and Descriptions For Animal
	// Category
	// https://api.publicapis.org/entries?category=Animals
	// Note : Category is Case Sensitive

	@GetMapping("/entries/categories")
	public ResponseEntity<List<EntryResponseDto>> getEntriesHandler(@RequestParam String category) {

		String url = "https://api.publicapis.org/entries?category=" + category;

		Apidata data = restTemplate.getForObject(url, Apidata.class);

		List<Entry> apiEntries = data.getEntries();

		List<EntryResponseDto> collect = apiEntries.stream().map(e -> this.modelMapper.map(e, EntryResponseDto.class))
				.collect(Collectors.toList());

		return new ResponseEntity<List<EntryResponseDto>>(collect, HttpStatus.OK);
	}

	@PostMapping("/entries")
	public ResponseEntity<ApiResponse> saveEntryHandler(@RequestBody Entry entry) throws ResourceNotFoundException {

		ApiResponse apiResponse = this.entryServices.saveEntry(entry);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
	}

}
