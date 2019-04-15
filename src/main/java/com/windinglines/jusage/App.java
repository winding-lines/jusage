package com.windinglines.jusage;

import com.windinglines.jusage.full.Rewrite;
import com.windinglines.jusage.simple.Indexer;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jetbrains.annotations.NotNull;


public class App {
  public static void main(String[] args) {
    Options options = buildOptions();

    CommandLineParser cmdParser = new DefaultParser();
    CommandLine cmd=null;
    try {
      cmd = cmdParser.parse(options, args, true);
      if (cmd.getArgs().length != 2) {
        throw new ParseException("invalid number of arguments");
      }
    } catch (ParseException e) {
      System.err.println(e.getMessage());
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("jusage [options] package base_folder", options);
      System.exit(1);
    }

    String packageName = cmd.getArgs()[0];
    String baseFolder = cmd.getArgs()[1];
    if (cmd.hasOption("p")) {
      final Rewrite rewrite = new Rewrite();
      rewrite.foo();
    } else {
      simpleIndexer(packageName, baseFolder);
    }
  }

  private static void simpleIndexer(String packageName, String baseFolder) {
    Indexer indexer = new Indexer(baseFolder, packageName);
    try {
      indexer.build();
      indexer.write();
      System.out.println("Output " + indexer.getDbPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @NotNull
  private static Options buildOptions() {
    Options options = new Options();
    final Option debug = new Option("d", "debug", false, "show debugging information");
    options.addOption(debug);
    final Option parser =
        new Option("p", "full-parser", false, "use the java parser (needs shadow jar)");
    options.addOption(parser);
    return options;
  }
}
