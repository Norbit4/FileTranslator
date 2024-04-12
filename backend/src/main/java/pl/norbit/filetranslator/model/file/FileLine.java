package pl.norbit.filetranslator.model.file;

import lombok.Getter;

@Getter
public class FileLine extends FileObject{

    private final ToTranslate toTranslate;

    protected FileLine(String key, Object toTranslate) {
        super(key);
        this.toTranslate = new ToTranslate(toTranslate);
    }

    @Override
    public Object getTranslateValue() {
        return toTranslate.getTranslateValue();
    }

    @Override
    public Object getDefaultValue() {
        return toTranslate.getDefaultValue();
    }

    @Override
    public void setTranslate(String translate) {
        toTranslate.setTranslate(translate);
    }
}
