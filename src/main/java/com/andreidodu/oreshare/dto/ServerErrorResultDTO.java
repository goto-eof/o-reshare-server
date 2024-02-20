package com.andreidodu.oreshare.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServerErrorResultDTO {
    private int errorCode;
    private List<String> messageList = new ArrayList<>();

    public ServerErrorResultDTO(int errorCode, String message) {
        this.errorCode = errorCode;
        this.messageList.add(message);
    }

}
