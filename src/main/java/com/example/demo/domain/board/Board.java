package com.example.demo.domain.board;

import jakarta.validation.constraints.NotBlank;

public class Board {

    private Long id;

    @NotBlank(message = "게시판 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "게시판 설명은 필수입니다.")
    private String description;

    public Board() {}

    public Board(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
