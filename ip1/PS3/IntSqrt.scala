// For tutorial question 3.2
// Finding integer square roots, using binary search and ternary search

object IntSqrt{
  // Find a s.t. a^2 <= y < (a+1)^2.  Precondition: y >= 0.  First version
  // uses a straight-forward linear search.
  def linearSqrt(y:Int) : Int = {
    assert(y>=0);
    // Invariant I: a^2 <= y
    var a = 0;
    while((a+1)*(a+1) <= y) a = a+1
    // a^2 <= y < (a+1)^2
    return a
  }

  // Find a s.t. a^2 <= y < (a+1)^2.  Precondition: y >= 0.
  def ternsqrt(y:Int) : Int = {
    require(y>=0)
    // Invariant I: a^2 <= y < b^2 and a<b
    var a = 0; var b = y+1;
    while(a+1<b){
      val m1 = (2*a+b)/3
      val m2 = (a+2*b)/3

      if(m2*m2<=y){a=m2}             // Right third  
      else if (m1*m1<=y) {a=m1;b=m2} // Middle third
      else {b=m1}                    // Left third
    }
    // I and a+1=b, so a^2 <= y < (a+1)^2
    return a
  }

  def mainPartA(args:Array[String]) = {
    for (y<-0 to 65536 /*2^16*/) assert(binsqrt(y) == ternsqrt(y))
  }

  def ternsqrtAnnotated(y:Int) : Int = {
    require(y>=0)
    // Invariant I: a^2 <= y < b^2 and a<b
    var a = 0; var b = y+1;
    while(a+1<b){
      val m1 = (2*a+b)/3
      val m2 = (a+2*b)/3
      assert(a<=m1); assert(m1<m2); assert(m2<b)
      if (a == m1)
      {
        // Note: invariant has a^2 <= y therefore m1^2 <= y
        //       therefore left-most (empty) branch not taken
        assert(b==a+2); assert(m2==m1+1)
        if (m1!=0) assert(m1 <= y/m1)  //assert(m1*m1 <= y)
      }

      if(m2*m2<=y){a=m2}             // Right third  
      else if (m1*m1<=y) {a=m1;b=m2} // Middle third
      else {b=m1}                    // Left third
    }
    // I and a+1=b, so a^2 <= y < (a+1)^2
    return a
  }


  // Find a s.t. a^2 <= y < (a+1)^2.  Precondition: y >= 0.
  def ternsqrtNoOverflow(y:Int) : Int = {
    require(y>=0)
    // Invariant I: a^2 <= y < b^2 and a<b
    var a = 0; var b = y+1;
    while(a+1<b){
      val m1 = a + (b-a)/3     
      var m2 = 0
      if ((b/3)<a+1) m2 = a + (2*(b-a))/3  // narrow interval - exact
      else           m2 = a + 2*((b-a)/3)  // wide  - approximate

      if(m2<=y/m2){a=m2}             // Right third  
      else if (m1<=y/m1) {a=m1;b=m2} // Middle third
      else {b=m1}                    // Left third
    }
    a
  }

  // Find a s.t. a^2 <= y < (a+1)^2.  Precondition: y >= 0.
  def binsqrt(y:Int) : Int = {
    assert(y>=0);
    // Invariant I: a^2 <= y < b^2 and a<b
    var a = 0; var b = y+1;
    while(a+1<b){
      //val m = (a+b)/2; // a < m < b
      val m = a+(b-a)/2; // a < m < b
      //if(m*m<=y) a=m else b=m;
      if(m<=y/m) a=m else b=m;
    }
    // I and a+1=b, so a^2 <= y < (a+1)^2
    return a
  }


  def main(args:Array[String]) = {
    for (y<-0 to 10000000) assert(binsqrt(y) == ternsqrtNoOverflow(y)) // Up to 1E7 takes about 2 seconds
    //for (y<-0 to 100000000) assert(binsqrt(y) == ternsqrtNoOverflow(y)) // Up to 1E8 takes about 20 seconds
    //for (y<-1 to 2147483647) {assert(binsqrt(y) == ternsqrtNoOverflow(y))} // Up to Int.MaxValue takes about 8 minutes
  }
}

