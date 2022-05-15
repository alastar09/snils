package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class snils {
    @Id
    @NotEmpty
    @NotNull
    private String snils_num;

    public String getSnils_num() {
        return snils_num;
    }

    public void setSnils_num(String snils_num) {
        this.snils_num = snils_num;
    }
}
