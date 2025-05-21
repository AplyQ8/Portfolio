package components.board;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tile extends GameBoardComponent {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("tileType")
    private String name;

    @Override
    public String to_String() {
        return this.toString();
    }
}
