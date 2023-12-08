package io.codemodder.sample;

import io.codemodder.testutils.CodemodTestMixin;
import io.codemodder.testutils.Metadata;

@Metadata(
        codemodType = ReadLinesCodemod.class,
        testResourceDir = "modernize-readlines",
        dependencies = {})
final class ReadLinesCodemodTest implements CodemodTestMixin { }
