package com.aranhid.azuredictors;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface AzureDictorsAPI {
    String API_URL = "https://eastasia.api.cognitive.microsoft.com";
    String key = "1db01788ad86488d90d573a7fe502c11 ";
    @POST("/sts/v1.0/issueToken") // путь к API
    @Headers({
            "Content-type: application/x-www-form-urlencoded",
            "Content-Length: 0",
            "Ocp-Apim-Subscription-Key: "+key
    })
        // String - формат ответа от сервера
        // Тип ответа - String, действие - getToken, содержание запроса - пустое (нет полей формы)
    Call<String> getToken();

    // TODO: используя аннотацию @GET без параметров описываем запрос для получения списка дикторов
    // передаём адрес (@Url) и токен в заголовке (@Header)
    // см. пример использования в документации https://square.github.io/retrofit/2.x/retrofit/retrofit2/http/Header.html
    @GET() // TODO: учитывайте при вызове getDictors, что содержимое токена сопровождается словом Bearer
    Call<ArrayList<Dictor>> getDictors(@Url String url, @Header("Authorization") String token);
}
