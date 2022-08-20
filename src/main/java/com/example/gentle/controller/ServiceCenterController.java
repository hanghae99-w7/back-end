package com.example.gentle.controller;

import com.example.gentle.dto.requestDto.ServiceCenterRequestDto;
import com.example.gentle.service.ServiceCenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/servicecenter")
public class ServiceCenterController {

    private final ServiceCenterService serviceCenterService;

    public ServiceCenterController(ServiceCenterService serviceCenterService) {
        this.serviceCenterService = serviceCenterService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createContact(@RequestBody ServiceCenterRequestDto serviceCenterRequestDto,
                                           HttpServletRequest request) {

        return serviceCenterService.createContact(serviceCenterRequestDto, request);

    }

}
