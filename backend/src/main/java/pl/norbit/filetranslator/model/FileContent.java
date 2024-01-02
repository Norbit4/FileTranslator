package pl.norbit.filetranslator.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileContent {

    private final List<TranslateGroup> groups;
    private String fileName;

    public FileContent() {
        this.groups = new ArrayList<>();
    }

    public void addLine(FileLine line) {
        TranslateGroup group = groups.size() > 0 ? groups.get(groups.size() - 1) : null;

        if(group == null) {
            group = new TranslateGroup();
            groups.add(group);
        }

        Status status = group.addLine(line);

        if(status != Status.FULL) return;

        TranslateGroup translateGroup = new TranslateGroup();

        translateGroup.addLine(line);

        groups.add(translateGroup);
    }
}
