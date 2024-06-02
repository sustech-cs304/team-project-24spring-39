package com.example.cs304.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private String content;
    private String receiverId;
    private String endTime;
    private String type;
}
