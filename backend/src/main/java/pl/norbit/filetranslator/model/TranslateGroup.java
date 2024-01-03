package pl.norbit.filetranslator.model;

import lombok.Data;
import pl.norbit.filetranslator.enums.SizeStatus;

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

    public SizeStatus addLine(FileLine line) {
        // 50 strings is max for one request in DeepL
        if(lines.size() == 50) return SizeStatus.FULL;

        lines.add(line);

        return SizeStatus.OK;
    }
}
