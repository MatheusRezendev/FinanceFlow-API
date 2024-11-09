package org.example.financeflowapi.domain.model.repository.service.exception.service;

import java.util.List;

public interface ICRUDService<Request, Response> {

    List<Response> obterTodos();

    Response obterPorId(Long id);

    Response cadastrar(Request dto);

    Response atualizar(Long id,Request dto);

    void remover(Long id);
}
