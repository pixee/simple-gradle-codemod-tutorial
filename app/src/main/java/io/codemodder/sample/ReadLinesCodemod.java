package io.codemodder.sample;

import com.contrastsecurity.sarif.Result;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import io.codemodder.*;
import io.codemodder.providers.sarif.semgrep.SemgrepScan;

import javax.inject.Inject;

import static io.codemodder.ast.ASTTransforms.removeImportIfUnused;
import static io.codemodder.javaparser.JavaParserTransformer.replace;

@Codemod(
        id = "pixee:java/migrate-files-commons-io-to-nio",
        reviewGuidance = ReviewGuidance.MERGE_WITHOUT_REVIEW)
public final class ReadLinesCodemod extends SarifPluginJavaParserChanger<MethodCallExpr> {
    private static final String RULE =
            """
            rules:
              - id: migrate-files-commons-io-to-nio-read-file-to-lines
                pattern-either:
                  - pattern: (org.apache.commons.io.FileUtils).readLines($X)
                  - pattern: (org.apache.commons.io.FileUtils).readLines($X, (Charset $Y))
            """;

    @Inject // tell Guice to inject stuff here
    public ReadLinesCodemod(
            @SemgrepScan(yaml = RULE) final RuleSarif sarif // asks for callbacks for hits on the given Semgrep rule
    ) {
        super(
            sarif, // give it the SARIF that will be used to find hits
            MethodCallExpr.class, // only look for method calls that match the SARIF
            CodemodReporterStrategy.fromClasspath(ReadLinesCodemod.class) // pull storytelling text from classpath resources
        );
    }

    @Override
    public boolean onResultFound(
            CodemodInvocationContext context, // some context, like the file path, configuration, etc.
            CompilationUnit cu, // the JavaParser model of this source file source
            MethodCallExpr apacheReadLinesCall, // the method call AST node that was found by Semgrep in our query
            Result result // the SARIF result for this particular finding
    ) {
        NodeList<Expression> arguments = apacheReadLinesCall.getArguments(); // get the arguments for the call
        MethodCallExpr toPath = new MethodCallExpr(arguments.get(0), "toPath"); // call toPath() on the first arg)
        arguments.set(0, toPath);
        boolean success =
                replace(apacheReadLinesCall)
                        .withStaticMethod("java.nio.file.Files", "readAllLines")
                        .withSameArguments();
        if (success) {
            removeImportIfUnused(cu, "org.apache.commons.io.FileUtils"); // won't remove if it's still needed
        }
        return success; // should return true if a change was made, so it can be communicated to the user
    }
}
