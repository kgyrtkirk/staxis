/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package staxis;

import java.util.Iterator;

public abstract class ICSeriesGenerator<IteratorContext> implements SeriesGenerator {
  private int maxValue;

  public ICSeriesGenerator(int n) {
    this.maxValue = n;
  }

  @Override
  public Iterator<Double> iterator() {
    return new Iterator<Double>() {

      double val = 0;
      IteratorContext ic = newIteratorContext();

      @Override
      public Double next() {
        if (!hasNext()) {
          throw new RuntimeException();
        }
        return f(ic, val++);
      }

      @Override
      public boolean hasNext() {
        return val < maxValue;
      }
    };
  }

  @Override
  public int getN() {
    return maxValue;
  }

  protected abstract IteratorContext newIteratorContext();

  protected abstract Double f(IteratorContext ic, double d);

}
