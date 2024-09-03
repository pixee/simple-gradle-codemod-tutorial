package io.codemodder.sample;

import static io.codemodder.ast.ASTTransforms.addImportIfMissing;

import com.contrastsecurity.sarif.Result;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import io.codemodder.*;
import io.codemodder.javaparser.ChangesResult;
import io.codemodder.providers.sarif.semgrep.SemgrepScan;
import java.util.Optional;
import javax.inject.Inject;

/** Adds a decorator @DoesDeserialization for types that are serializable for historic reasons. This is a common pattern for many custom codemod cases where a missing annotation that might enforce authentication or access control on a given type/method. */
@Codemod(
        id = "pixee:java/add-decorator",
        reviewGuidance = ReviewGuidance.MERGE_WITHOUT_REVIEW,
        importance = Importance.MEDIUM,
        executionPriority = CodemodExecutionPriority.LOW)
public final class AddDecoratorCodemod
        extends SarifPluginJavaParserChanger<ClassOrInterfaceDeclaration> {

    private static final String DETECTION_RULE =
            """
                  rules:
                    - id: add-decorator
                      pattern: public class $CLAZZ implements Serializable
                  """;

    @Inject
    public AddDecoratorCodemod(@SemgrepScan(yaml = DETECTION_RULE) final RuleSarif sarif) {

        // usually we don't have to specify a particular RegionNodeMatcher, but JavaParser returns the range including everything from beginning of class to end of class, so we relax the matching logic to just match the given line so it lines up with what Semgrep reports
        super(
                sarif,
                ClassOrInterfaceDeclaration.class,
                RegionNodeMatcher.MATCHES_LINE);
    }

    @Override
    public ChangesResult onResultFound(
            final CodemodInvocationContext context,
            final CompilationUnit cu,
            final ClassOrInterfaceDeclaration classOrInterfaceDeclaration,
            final Result result) {
        Optional<AnnotationExpr> dummyAnnotation =
                classOrInterfaceDeclaration.getAnnotationByName("DoesSerialization");
        if (dummyAnnotation.isEmpty()) {
            classOrInterfaceDeclaration.addAnnotation("DoesSerialization");
            addImportIfMissing(cu, "com.acme.DoesSerialization");
            return ChangesResult.changesApplied;
        }
        return ChangesResult.noChanges;
    }
}
