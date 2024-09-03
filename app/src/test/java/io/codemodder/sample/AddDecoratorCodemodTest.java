package io.codemodder.sample;

import io.codemodder.testutils.CodemodTestMixin;
import io.codemodder.testutils.Metadata;

@Metadata(
        codemodType = AddDecoratorCodemod.class,
        testResourceDir = "add-decorator",
        dependencies = {})
final class AddDecoratorCodemodTest implements CodemodTestMixin { }
