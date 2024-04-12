package pl.norbit.filetranslator.model.file;

import lombok.Getter;
import lombok.Setter;

public class ToTranslate {

    @Getter
    private final Object defaultValue;

    @Setter
    private String translate;

    public String getDefault() {
        return defaultValue.toString();
    }

    public ToTranslate(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getTranslateValue() {
        if (defaultValue instanceof String) {
            return translate;
        }

        return defaultValue;
    }
}
