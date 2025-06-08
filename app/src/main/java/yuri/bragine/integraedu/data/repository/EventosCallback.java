package yuri.bragine.integraedu.data.repository;

import java.util.List;

import yuri.bragine.integraedu.data.models.Evento;

public interface EventosCallback {
     void onSuccess(List<Evento> eventos);
     void onError(String errorMessage);
 }