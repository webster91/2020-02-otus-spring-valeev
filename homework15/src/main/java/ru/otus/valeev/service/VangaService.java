package ru.otus.valeev.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(errorChannel = "exceptionVangaChanel")
public interface VangaService {

    @Gateway(requestChannel = "inVangaPredicate", replyChannel = "outVangaPredicate")
    String getPredicate(String book);
}
