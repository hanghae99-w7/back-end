package com.example.gentle.controller;

import com.example.gentle.dto.requestDto.ServiceCenterRequestDto;
import com.example.gentle.service.ServiceCenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/servicecenter")
public class ServiceCenterController {

    private final ServiceCenterService serviceCenterService;

    public ServiceCenterController(ServiceCenterService serviceCenterService) {
        this.serviceCenterService = serviceCenterService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createContact(@RequestBody ServiceCenterRequestDto requestDto,
                                           HttpServletRequest request) {
        return serviceCenterService.createContact(requestDto, request);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getContact(HttpServletRequest request) {
        return serviceCenterService.getContact(request);
    }

    @RequestMapping(value = "/admin/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateAdminCheck(@PathVariable Long id,
                                              HttpServletRequest request) {
        return serviceCenterService.updateAdminCheck(id, request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateContact(@PathVariable Long id,
                                           @RequestBody ServiceCenterRequestDto requestDto,
                                           HttpServletRequest request) {
        return serviceCenterService.updateContact(id, requestDto, request);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteContact(@PathVariable Long id,
                                           HttpServletRequest request) {
        return serviceCenterService.deleteContact(id, request);
    }

}
