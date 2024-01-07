package com.uexcel.airlinebookingreservation.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@ControllerAdvice
public class HandlerOfException {
    @ResponseBody
    @ExceptionHandler(IncorrectDataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void NotFound(IncorrectDataException message,
                         HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (request.getServletPath().equals("/booking/check-booking")) {
            response.sendRedirect("/booking/check-booking?error=" + message.getMessage());
        }

        if (request.getServletPath().equals("/booking/update")) {
            response.sendRedirect("/booking/update?error=" + message.getMessage());
        }

        if (request.getServletPath().equals("/booking/flight_schedule")) {
            response.sendRedirect("/booking?error=" + message.getMessage());
        }

        if (request.getServletPath().equals("/booking/book")) {
            HttpSession session = request.getSession();
            int flightId = (int) session.getAttribute("currentFlight_id");
            response.sendRedirect("/booking/book?error=" + message.getMessage()+"&flight_id="+flightId);
        }

        if (request.getServletPath().equals("/booking/save-update")) {
            response.sendRedirect("/booking/update?error=The update failed because seat was not selected.");
        }
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(java.lang.NullPointerException.class)
    public void nullPointerException(java.lang.NullPointerException e, HttpServletResponse response) throws IOException {
        response.sendRedirect("/booking");
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    public void idNotFoundException(org.springframework.web.bind.MissingServletRequestParameterException e,
                                    HttpServletResponse response) throws IOException {
        response.sendRedirect("/booking");
    }
}
