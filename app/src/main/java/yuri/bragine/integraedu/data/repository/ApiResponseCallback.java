package yuri.bragine.integraedu.data.repository;

public interface ApiResponseCallback {
    void onSuccess(String jsonResponse);
    void onError(String errorMessage);
}
