
/* [Q3]

bash commands to compile and run:

fsc -cp ./scalatest_2.12-3.0.5.jar:./scalactic_2.12-3.0.5.jar AshleyPS2Q5.scala question5.scala
scala -cp ./scalatest_2.12-3.0.5.jar:./scalactic_2.12-3.0.5.jar org.scalatest.run TestQ5

*/

import org.scalatest.FunSuite
import question5.search

class TestQ5 extends FunSuite{
  // (a) On line 6, replace false by true.
  test("var found initially set to false"){
    assert(search(Array('a'), Array('b')) === false)
  }

  // (b) On line 7, replace <= by <.
  // Test data will not reveal this bug, code gives the correct answer

  // (c) On line 7, replace N-K by N-K+1.
  // Test data will not reveal this bug, code gives the correct answer

  // (d) On line 10, replace 0 by 1.
  test("starts checking with the first character of the patten"){
    assert(search(Array('b','a'), Array('c','a')) === false)
  }

  // (e) On line 11, replace < by <=.
  // Test data will not reveal this bug because an exception is thrown
  // Lines 30-32 fail normally but suceed if this exception is thrown
  /* test("more than the number of characters in pattern"){
    intercept[ArrayIndexOutOfBoundsException] { search(Array('b','a'), Array('c','b','a')) }
     } */

  // (f) On line 12, replace == by >=.
  // Test data will not reveal this bug, code gives the correct answer

}




