package ru.otus.valeev.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface VangaService {

    @Gateway(requestChannel = "inVangaPredicate", replyChannel = "outVangaPredicate")
    String getPredicate(String book);
}
