package com.mju.app.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.mju.app.demo.controller.LocationPlaceController;;

@SpringBootApplication
public class MjuAppServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MjuAppServiceApplication.class, args);
	}

}
