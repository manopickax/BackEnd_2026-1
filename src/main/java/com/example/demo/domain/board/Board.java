package com.example.demo.domain.board;

import jakarta.validation.constraints.NotBlank;

public class Board {

    private Long id;

    @NotBlank(message = "게시판 이름은 필수입니다.")
    private String name;

    public Board() {}

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
