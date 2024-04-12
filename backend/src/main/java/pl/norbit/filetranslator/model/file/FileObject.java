package pl.norbit.filetranslator.model.file;

import lombok.Getter;
import java.util.UUID;

@Getter
public abstract class FileObject {

    private final UUID id;
    private final String key;

    protected FileObject(String key) {
        this.key = key;
        this.id = UUID.randomUUID();
    }

    public abstract Object getTranslateValue();

    public abstract Object getDefaultValue();

    public abstract void setTranslate(String translate);
}
