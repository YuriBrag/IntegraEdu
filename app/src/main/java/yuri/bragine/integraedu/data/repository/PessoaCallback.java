package yuri.bragine.integraedu.data.repository;
import yuri.bragine.integraedu.data.models.Pessoa;

public interface PessoaCallback {

    void onSuccess(Pessoa pessoa);
    void onError(String errorMessage);
}
