/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.example;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MyBenchmark {

    @State(Scope.Thread)
    public static class MyState {
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Trie trie = new Trie();

        String stringToFind;

        @Setup(Level.Trial)
        public void doSetup() {
            RandomStringGenerator randomStringGenerator =
                    new RandomStringGenerator.Builder()
                            .withinRange('0', 'z')
                            .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                            .build();

//            String d = randomStringGenerator.generate(20);
//            trie.add(d);
//            stringToFind = d;
//            int numberOfElements = (int) ((Math.random() * (10000 - 1)) + 1);
            int numberOfElements = 2900000;

            for (int i = 0; i < numberOfElements; i++) {
//                int length = (int) ((Math.random() * (500 - 1)) + 1);
                int length = 20;
                String generate = randomStringGenerator.generate(length);
//                list.add(generate);
//                set.add(generate);
                trie.add(generate);
                stringToFind = generate;
            }

            String d = randomStringGenerator.generate((int) ((Math.random() * (500 - 1)) + 1));
            stringToFind = d;
        }
    }

    @Warmup(iterations = 5)
    @Measurement(iterations = 10)
    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testMethod(MyState generator) {
        generator.trie.contains(generator.stringToFind);
    }

}