package org.example.test_unibell.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Long clientId;
    private String typeName;
    private String contactValue;
}