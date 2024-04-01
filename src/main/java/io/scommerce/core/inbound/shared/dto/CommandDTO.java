package io.scommerce.core.inbound.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandDTO {
    private Long pk;
    private String name;
    private String user;
    private String channel;
}
