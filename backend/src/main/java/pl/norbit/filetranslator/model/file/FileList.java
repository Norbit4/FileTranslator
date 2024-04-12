package pl.norbit.filetranslator.model.file;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FileList extends FileObject{

    private final List<ToTranslate> toTranslate;
    private int lastTranslateIndex = 0;

    protected FileList(String key, List<String> toTranslate) {
        super(key);
        this.toTranslate = new ArrayList<>();

        toTranslate.forEach(line -> this.toTranslate.add(new ToTranslate(line)));
    }

    @Override
    public Object getTranslateValue() {
        return toTranslate.stream()
                .map(ToTranslate::getTranslateValue)
                .toList();
    }

    @Override
    public Object getDefaultValue() {
        return toTranslate.stream()
                .map(ToTranslate::getDefaultValue)
                .toList();
    }

    @Override
    public void setTranslate(String translate) {
        if(lastTranslateIndex < toTranslate.size()){
            toTranslate.get(lastTranslateIndex).setTranslate(translate);
            lastTranslateIndex++;
        }
    }
}
