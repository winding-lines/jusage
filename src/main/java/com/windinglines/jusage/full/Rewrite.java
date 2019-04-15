package com.windinglines.jusage.full;

import com.netflix.rewrite.ast.Tr;
import com.netflix.rewrite.parse.OracleJdkParser;
import com.netflix.rewrite.parse.Parser;


/**
 * Work in progress toward integrating Netflix's rewrite parser.
 */
public class Rewrite {
  Parser _parser = new OracleJdkParser();

  public void foo() {
    String pre = "package com.windinglines.foo; public class Universal{ public void bar() {}}";
    String a = "import com.windinglines.foo; class A{ static void foo() {}}";
    final Tr.CompilationUnit unit = _parser.parse(a, pre);
  }
}
