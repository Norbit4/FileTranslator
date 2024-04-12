package pl.norbit.filetranslator.model;

import java.util.List;

public record TranslateGroup(List<TranslateRequest> translateRequests) {
}
