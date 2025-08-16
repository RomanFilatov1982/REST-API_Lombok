package models.lombok;

import lombok.Data;

@Data
public class SingleUserResponseLombokTest {
    public Data data;
    public Support support;

    @lombok.Data
    public class Data {
        int id;
        String email, first_name, last_name, avatar;
    }

    @lombok.Data
    public class Support {
        String url, text;
    }
}
