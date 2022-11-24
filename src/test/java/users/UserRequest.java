package users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("job")
    private String job;

    public UserRequest() {
        this.name = "randomName" + (Math.random() * 99999);
        this.job = "randomJob" + (Math.random() * 99999);
    }
}
