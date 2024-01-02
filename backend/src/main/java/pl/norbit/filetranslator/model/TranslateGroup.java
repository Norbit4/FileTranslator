package pl.norbit.filetranslator.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TranslateGroup {

    private final List<FileLine> lines;

    public TranslateGroup() {
        this.lines = new ArrayList<>();
    }

    public void addTranslate(int index, String translate) {
        lines.get(index).setTranslate(translate);
    }

    public Status addLine(FileLine line) {
        // 50 strings is max for one request in DeepL
        if(lines.size() == 50) return Status.FULL;

        lines.add(line);

        return Status.OK;
    }
}
