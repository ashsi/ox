
/* [Q3]

bash commands to compile and run:

fsc -cp ./scalatest_2.12-3.0.5.jar:./scalactic_2.12-3.0.5.jar AshleyPS2Q3.scala 
scala -cp ./scalatest_2.12-3.0.5.jar:./scalactic_2.12-3.0.5.jar org.scalatest.run TestQ3

*/

import org.scalatest.FunSuite

class TestQ3 extends FunSuite{
  
  //using names as an example to test that 3 standard functions work for Array[String]
  val names = Array("Cecilia", "Ben", "Dan", "Amanda", "Cecil", "Amy")
  
  test("names sorted"){ 
    //Tests .sorted
    val sortedNames = Array("Amanda", "Amy", "Ben", "Cecil", "Cecilia", "Dan")
    assert(names.sorted === sortedNames) 
  }
  test("names in reverse order"){
    //Tests .reverse
    //Will fail if test fails for .sorted
    val reversedNames = Array("Dan", "Cecilia", "Cecil", "Ben", "Amy", "Amanda")
    assert(names.sorted.reverse === reversedNames)
  }
  test("last name in list"){
    //Tests .last
    val lastName = "Amy"
    assert(names.last === lastName)
  }
}