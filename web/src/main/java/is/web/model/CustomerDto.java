package is.web.model;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomerDto {
    private int id;
    private String firstName;
    private String lastName;
}
