package pl.norbit.filetranslator.model;

import pl.norbit.filetranslator.model.file.ToTranslate;

import java.util.UUID;

public record TranslateRequest(UUID id, ToTranslate toTranslate) {
}
