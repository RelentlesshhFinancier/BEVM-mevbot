/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.console.project.unit.gen;

import java.io.File;
import java.io.IOException;

import org.web3j.codegen.Console;

import static java.io.File.separator;
import static org.web3j.utils.Collection.tail;

public class Generator {

    public static void main(String[] args) throws Exception {
        args = tail(args);
        if (args.length == 0) {
            throw new Exception("generate <project_directory>");
        }
        File pathToJavaFiles;
        if (args.length == 1) {
            pathToJavaFiles =
                    new File(
                            args[0]
                                    + separator
                                    + "build"
                                    + separator
                                    + "generated"
                                    + separator
                                    + "source"
                                    + separator
                                    + "web3j"
                                    + separator
                                    + "main"
                                    + separator
                                    + "java");
        } else {
            pathToJavaFiles = new File(args[1]);
        }

        ClassProvider classProvider = new ClassProvider(pathToJavaFiles);

        String[] finalArgs = args;
        classProvider
                .getClasses()
                .forEach(
                        c -> {
                            try {
                                new UnitClassGenerator(
                                                c,
                                                c.getCanonicalName()
                                                        .substring(
                                                                0,
                                                                c.getCanonicalName()
                                                                        .lastIndexOf(".")),
                                                finalArgs[0])
                                        .writeClass();
                            } catch (IOException e) {
                                Console.exitError("Can't find project");
                            }
                        });
    }
}
