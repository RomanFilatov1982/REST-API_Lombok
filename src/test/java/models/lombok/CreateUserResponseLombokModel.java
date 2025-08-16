package models.lombok;

import lombok.Data;

@Data
public class CreateUserResponseLombokModel {
    String name, job, createdAt;
    int id;
}
