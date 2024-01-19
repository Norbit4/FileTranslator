package pl.norbit.filetranslator.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class FileLine {

    private String key;
    private Object toTranslate;
    private String translate;

    public FileLine(String key, Object toTranslate) {
        this.key = key;
        this.toTranslate = toTranslate;
    }

    public Object getTranslateValue(){
        if(toTranslate instanceof Integer){
            return toTranslate;
        } else if (toTranslate instanceof List<?> list) {
            if(!list.isEmpty() && list.get(0) instanceof String){
                return Arrays.asList(translate.substring(1, translate.length() - 1).split(", "));
            }

            return toTranslate;
        }

        return translate;
    }
}
