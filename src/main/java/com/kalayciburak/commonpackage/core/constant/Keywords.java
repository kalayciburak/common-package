package com.kalayciburak.commonpackage.core.constant;

import java.util.ArrayList;
import java.util.List;

import static com.kalayciburak.commonpackage.core.enums.Languages.EN_CREATION;
import static com.kalayciburak.commonpackage.core.enums.Languages.TR_CREATION;

public final class Keywords {
    private Keywords() {}

    public static final List<String> creationKeywords = new ArrayList<>();

    static {
        creationKeywords.addAll(TR_CREATION.getKeywords());
        creationKeywords.addAll(EN_CREATION.getKeywords());
    }
}