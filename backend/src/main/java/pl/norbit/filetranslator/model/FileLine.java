package pl.norbit.filetranslator.model;

import lombok.Data;

@Data
public class FileLine {

    private String key;
    private String text;
    private String translate;

    public FileLine(String key, String text) {
        this.key = key;
        this.text = text;
    }
}
